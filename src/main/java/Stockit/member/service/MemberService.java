package Stockit.member.service;

import Stockit.jwt.provider.JwtAuthTokenProvider;
import Stockit.jwt.token.AuthToken;
import Stockit.member.domain.Member;
import Stockit.member.domain.Role;
import Stockit.member.repository.MemberRepository;
import Stockit.member.vo.AuthRequest;
import Stockit.member.vo.RankVO;
import Stockit.order.domain.Order;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true) //읽기전용
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtAuthTokenProvider jwtAuthTokenProvider;
    private final static long LOGIN_RETENTION_MINUTES = 30;

    @Transactional //수정 가능
    public Long join(Member member) {
        validateDuplicateMember(member);//중복 회원 검증
        memberRepository.save(member);
        return member.getIdx();
    }

    //중복 회원 검증
    public void validateDuplicateMember(Member member) {
        boolean duplicated = findDuplicatedEmail(member.getEmail());
        if (duplicated) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //전체 회원 조회
    public List<Member> findAllMembers() {
        return memberRepository.findAll(Sort.by(Sort.Direction.ASC, "idx"));
    }

    //단일 회원 조회
    public Optional<Member> findMember(Long memberIdx) { return memberRepository.findById(memberIdx);}

    //랭킹 조회
    public List<RankVO> getRankList(){
        List<Member> members = memberRepository.findAll(Sort.by(Sort.Direction.DESC, "earningRate"));
        List<RankVO> ranking = new ArrayList<>();
        for (Member member: members) {
            ranking.add(new RankVO(member));
        }
        return ranking;
    }

    //닉네임 중복 검사
    public boolean findDuplicatedNickname(String nickname) {
        return memberRepository.existsByNickname(nickname);
    }

    //이메일 중복 검사
    public boolean findDuplicatedEmail(String email) {
        return memberRepository.existsByEmail(email);
    }

    //로그인시 회원 정보 불러오기
    public Optional<Member> login(AuthRequest authRequest) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                authRequest.getEmail(), authRequest.getPassword());
        //사용자 비번 체크, 패스워드 일치하지 않으면 AuthenticationException 발생
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        //로그인 성공하면 인증 객체 생성 및 스프링 시큐리티 저장
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Role role = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .map(Role::of)
                .orElse(Role.USER);

        return memberRepository.findByEmail(authRequest.getEmail());

    }

    //토큰 생성
    public AuthToken<Claims> createAuthToken(String email, Role role) {
        Date expiredDate = Date.from(LocalDateTime.now().plusMinutes(LOGIN_RETENTION_MINUTES).atZone(ZoneId.systemDefault()).toInstant());
        return jwtAuthTokenProvider.createAuthToken(email, role.name(), expiredDate);
    }

    //주문 조회
    public List<Order> findAllOrders(Long memberIdx) {
        return memberRepository.findById(memberIdx).orElseThrow(() -> new IllegalArgumentException("멤버가 없습니다.")).getOrders();
    }
}

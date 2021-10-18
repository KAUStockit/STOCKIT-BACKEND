package Stockit.member.service;

import Stockit.jwt.JwtUtil;
import Stockit.member.domain.Account;
import Stockit.member.domain.Member;
import Stockit.member.repository.MemberRepository;
import Stockit.member.vo.AuthRequest;
import Stockit.member.vo.RankVO;
import Stockit.member.vo.UserInfo;
import Stockit.order.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true) //읽기전용
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final static long LOGIN_RETENTION_MINUTES = 30;

    @Transactional //수정 가능
    public Long join(Member member) {
        validateDuplicateMember(member);//중복 회원 검증
        final Member savedMember = memberRepository.save(member);
        savedMember.createAccount(new Account());
        return savedMember.getIdx();
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
    public Optional<Member> findMember(Long memberIdx) {
        return memberRepository.findById(memberIdx);
    }

    //랭킹 조회
    public List<RankVO> getRankList() {
        List<Member> members = memberRepository.findAllByRank();
        List<RankVO> ranking = new ArrayList<>();
        for (Member member : members) {
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
    public UserInfo login(AuthRequest authRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));

        Optional<Member> optionalMember = memberRepository.findByEmail(authRequest.getEmail());
        String generatedToken = jwtUtil.generateToken(authRequest.getEmail());
        final Member member = optionalMember.orElseThrow(IllegalArgumentException::new);
        return new UserInfo(member, generatedToken);
    }

    //주문 조회
    public List<Order> findAllOrders(Long memberIdx) {
        return memberRepository.findById(memberIdx).orElseThrow(() -> new IllegalArgumentException("멤버가 없습니다.")).getOrders();
    }
}

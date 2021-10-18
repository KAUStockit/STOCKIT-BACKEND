package Stockit.member.service;

import Stockit.jwt.JwtUtil;
import Stockit.member.domain.Account;
import Stockit.member.domain.Member;
import Stockit.member.dto.LoginRequest;
import Stockit.member.dto.MemberInfo;
import Stockit.member.dto.RankingInfo;
import Stockit.member.repository.MemberRepository;
import Stockit.order.dto.OrderInfo;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        return savedMember.getId();
    }

    //중복 회원 검증
    public void validateDuplicateMember(Member member) {
        boolean duplicated = findDuplicatedEmail(member.getEmail());
        if (duplicated) throw new IllegalStateException("이미 존재하는 회원입니다.");
    }

    //전체 회원 조회
    public List<MemberInfo> findAllMembers() {
        return memberRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))
                .stream().map(MemberInfo::new).collect(Collectors.toList());
    }

    //단일 회원 조회
    public MemberInfo findMember(Long memberIdx) throws NotFoundException {
        final Optional<Member> optionalMember = memberRepository.findById(memberIdx);
        final Member member = optionalMember.orElseThrow(() -> new NotFoundException("멤버를 찾을 수 없습니다."));
        return new MemberInfo(member);
    }

    //랭킹 조회
    public List<RankingInfo> getRankList() {
        List<Member> members = memberRepository.findAllByRank();
        List<RankingInfo> ranking = new ArrayList<>();
        for (Member member : members) {
            ranking.add(new RankingInfo(member));
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
    public MemberInfo login(LoginRequest loginRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        Optional<Member> optionalMember = memberRepository.findByEmail(loginRequest.getEmail());
        String generatedToken = jwtUtil.generateToken(loginRequest.getEmail());
        final Member member = optionalMember.orElseThrow(IllegalArgumentException::new);
        return new MemberInfo(member, generatedToken);
    }

    //주문 조회
    public List<OrderInfo> findAllOrders(Long memberIdx) {
        return memberRepository.findById(memberIdx).orElseThrow(() -> new IllegalArgumentException("멤버가 없습니다.")).getOrders()
                .stream().map(OrderInfo::new).collect(Collectors.toList());
    }
}

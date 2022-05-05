package Stockit.domain.member;

import Stockit.config.security.jwt.JwtUtil;
import Stockit.infrastructure.member.MemberRepository;
import Stockit.interfaces.member.dto.LoginRequest;
import Stockit.interfaces.member.dto.MemberInfo;
import Stockit.interfaces.member.dto.MemberJoinRequest;
import Stockit.interfaces.member.dto.RankingInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    //단일 회원 조회
    public MemberInfo findMember(Long memberIdx) {
        final Optional<Member> optionalMember = memberRepository.findById(memberIdx);
        final Member member = optionalMember.orElseThrow(() -> new IllegalStateException("멤버를 찾을 수 없습니다."));
        return new MemberInfo(member);
    }

    //전체 회원 조회
    public List<MemberInfo> findAllMembers() {
        return memberRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))
                .stream().map(MemberInfo::new).collect(Collectors.toList());
    }

    //중복 회원 검증
    public void validateDuplicateMember(Member member) {
        boolean duplicated = findDuplicatedEmail(member.getEmail());
        if (duplicated) throw new IllegalStateException("이미 존재하는 회원입니다.");
    }

    //닉네임 중복 검사
    public boolean findDuplicatedNickname(String nickname) {
        return memberRepository.existsByNickname(nickname);
    }

    //이메일 중복 검사
    public boolean findDuplicatedEmail(String email) {
        return memberRepository.existsByEmail(email);
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

    //로그인시 회원 정보 불러오기
    public MemberInfo login(LoginRequest loginRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        Optional<Member> optionalMember = memberRepository.findByEmail(loginRequest.getEmail());
        String generatedToken = jwtUtil.generateToken(loginRequest.getEmail());
        final Member member = optionalMember.orElseThrow(IllegalArgumentException::new);
        return new MemberInfo(member, generatedToken);
    }

    @Transactional //수정 가능
    public Long join(MemberJoinRequest form) {
        Member member = new Member(form);
        validateDuplicateMember(member);//중복 회원 검증
        final Member savedMember = memberRepository.save(member);
        savedMember.createAccount(new Account());
        return savedMember.getId();
    }
}

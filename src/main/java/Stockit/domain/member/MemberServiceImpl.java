package Stockit.domain.member;

import Stockit.common.exception.EntityNotFoundException;
import Stockit.common.exception.IllegalStatusException;
import Stockit.config.security.jwt.JwtUtil;
import Stockit.interfaces.member.dto.LoginRequest;
import Stockit.domain.member.dto.MemberInfo;
import Stockit.interfaces.member.dto.MemberJoinRequest;
import Stockit.domain.member.dto.RankingInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberReader memberReader;
    private final MemberStore memberStore;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    //단일 회원 조회
    public MemberInfo getMember(final Long memberIdx) {
        final Optional<Member> member = memberReader.findMember(memberIdx);
        return new MemberInfo(member.orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public List<MemberInfo> getAllMembers() {
        final List<Member> memberList = memberReader.getAllActiveMembers();
        return memberList.stream()
                .map(MemberInfo::new)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isDuplicatedNickname(final String nickname) {
        final Optional<Member> member = memberReader.findMemberByNickname(nickname);
        return member.isPresent();
    }

    @Override
    public boolean isDuplicatedEmail(final String email) {
        final Optional<Member> member = memberReader.findMemberByEmail(email);
        return member.isPresent();
    }

    @Override
    public List<RankingInfo> getRankingList() {
        return memberReader.getAllByOrderByEarningRateDesc()
                .stream()
                .map(RankingInfo::new)
                .collect(Collectors.toList());
    }

    @Override
    public MemberInfo login(LoginRequest loginRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        Optional<Member> foundMember = memberReader.findMemberByEmail(loginRequest.getEmail());
        Member member = foundMember.orElseThrow(EntityNotFoundException::new);
        String generatedToken = jwtUtil.generateToken(loginRequest.getEmail());
        return new MemberInfo(member, generatedToken);
    }

    @Override
    public Long join(MemberJoinRequest form) {
        validateDuplicateMember(form.getEmail(), form.getNickname());//중복 회원 검증

        form.encodePassword(passwordEncoder.encode(form.getPassword()));
        Member member = new Member(form);
        final Member savedMember = memberStore.save(member);

        savedMember.createAccount(new Account());
        return savedMember.getId();
    }

    private void validateDuplicateMember(final String email, final String nickname) {
        if (memberReader.findMemberByEmail(email).isPresent()) {
            throw new IllegalStatusException("email is duplicated");
        }
        if (memberReader.findMemberByNickname(nickname).isPresent()) {
            throw new IllegalStatusException("nickname is duplicated");
        }
    }
}

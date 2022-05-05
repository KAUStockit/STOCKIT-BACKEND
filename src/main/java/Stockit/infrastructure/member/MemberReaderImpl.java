package Stockit.infrastructure.member;

import Stockit.domain.member.Member;
import Stockit.domain.member.MemberReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberReaderImpl implements MemberReader {

    private final MemberRepository memberRepository;

    @Override
    public Optional<Member> findMember(Long memberIdx) {
        return memberRepository.findById(memberIdx);
    }

    @Override
    public List<Member> getAllActiveMembers() {
        return memberRepository.findAll();
    }

    @Override
    public Optional<Member> findMemberByNickname(String nickname) {
        return memberRepository.findByNickname(nickname);
    }

    @Override
    public Optional<Member> findMemberByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    @Override
    public List<Member> getRankingList() {
        return memberRepository.findAllWithEarningRate();
    }
}

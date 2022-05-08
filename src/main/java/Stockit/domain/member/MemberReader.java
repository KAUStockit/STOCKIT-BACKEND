package Stockit.domain.member;

import java.util.List;
import java.util.Optional;

public interface MemberReader {
    Optional<Member> findMember(Long memberIdx);

    List<Member> getAllActiveMembers();

    Optional<Member> findMemberByNickname(String nickname);

    Optional<Member> findMemberByEmail(String email);

    List<Member> getAllByOrderByEarningRateDesc();
}

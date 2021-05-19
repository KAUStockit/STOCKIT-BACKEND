package Stockit.service;

import Stockit.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberService {

    //회원가입
    Long join(Member member);

    //회원 중복검사
    void validateDuplicateMember(Member member);

    //회원 전체 조회
    List<Member> findMembers();

    //회원 한 명 조회
    Optional<Member> findOne(Long memberId);

    //비밀번호 변경
    Long changePassword(Long id, String password);
}

package Stockit.repository;

import Stockit.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    //id로 회원 조회
    Optional<Member> findById(Long id);

    //name으로 회원 조회
    Optional<Member> findByName(String name);

    //nickname으로 회원 조회
    Optional<Member> findByNickname(String nickname);

    //email로 회원 조회
    Optional<Member> findByEmail(String email);

    //회원 저장
    Member save(Member member);

    //회원목록 전체 조회
    List<Member> findAll();

    //테스트 용도
    void clearStore();

}

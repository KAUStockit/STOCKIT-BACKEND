package Stockit.member.repository;

import Stockit.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository //스프링 빈으로 등록, JPA 예외를 스프링 기반 예외로 변환
public interface MemberRepository extends JpaRepository<Member, Long> {

    Boolean existsByEmail(String email);
    Boolean existsByNickname(String nickname);
    Optional<Member> findByEmail(String email);
}

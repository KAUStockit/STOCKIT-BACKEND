package Stockit.infrastructure.member;

import Stockit.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository //스프링 빈으로 등록, JPA 예외를 스프링 기반 예외로 변환
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

    Optional<Member> findByNickname(String nickname);

    @Query(value = "SELECT m.* FROM member m JOIN account acc ON m.account_id = acc.account_id ORDER BY acc.earning_rate DESC", nativeQuery = true)
    List<Member> getAllByOrderByEarningRateDesc();

    List<Member> findAll();
}

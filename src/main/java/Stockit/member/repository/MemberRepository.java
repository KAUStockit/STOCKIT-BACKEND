package Stockit.member.repository;

import Stockit.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository //스프링 빈으로 등록, JPA 예외를 스프링 기반 예외로 변환
public interface MemberRepository extends JpaRepository<Member, Long> {

    Boolean existsByEmail(String email);
    Boolean existsByNickname(String nickname);
    Optional<Member> findByEmail(String email);

    @Modifying
    @Query(value = "UPDATE member m SET m.balance = m.balance + :money WHERE m.member_idx = :memberId", nativeQuery = true)
    void updateBalance(@Param("memberId") long memberId, @Param("money") int money);
}

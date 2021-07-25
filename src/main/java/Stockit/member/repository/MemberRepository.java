package Stockit.member.repository;

import Stockit.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository //스프링 빈으로 등록, JPA 예외를 스프링 기반 예외로 변환
public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query("select m from Member m where m.email = :email")
    List<Member> findByEmail(@Param("email") String email);

    //findAllByOrderByIdAsc
    @Query("select m from Member m order by m.earningRate desc, m.nickname")
    List<Member> findAllOrderByEarningRateDesc();
}

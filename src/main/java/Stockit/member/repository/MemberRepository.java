package Stockit.member.repository;

import Stockit.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository //스프링 빈으로 등록, JPA 예외를 스프링 기반 예외로 변환
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }


    public List<Member> findByEmail(String email) {
        return em.createQuery("select m from Member m where m.email = :email", Member.class)
                .setParameter("email", email)
                .getResultList();
    }
    //findAllByOrderByIdAsc
    public List<Member> findAllByOrderByEarningRateDesc() {
        return em.createQuery("select m from Member m order by m.earningRate desc ", Member.class)
                .getResultList();
    }
}

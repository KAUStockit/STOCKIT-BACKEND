package Stockit.infrastructure.member;

import Stockit.domain.member.Account;
import Stockit.domain.member.Member;
import Stockit.interfaces.member.dto.MemberJoinRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void getAllByOrderByEarningRateDesc() {
        Member memberA = new Member("testA", "testA@test.com", "nickTestA", "1234");
        memberA.createAccount(new Account());
        memberA.getAccount().updateEarningRate(10.0);
        Member memberB = new Member("testB", "testB@test.com", "nickTestB", "5678");
        memberB.createAccount(new Account());
        memberB.getAccount().updateEarningRate(5.0);
        Member savedMemberA = memberRepository.save(memberA);
        Member savedMemberB = memberRepository.save(memberB);

        List<Member> allByOrderByEarningRateDesc = memberRepository.getAllByOrderByEarningRateDesc();

        Assertions.assertThat(allByOrderByEarningRateDesc)
                .containsExactly(savedMemberA, savedMemberB);
        Assertions.assertThat(savedMemberA.getAccount().getEarningRate())
                .isGreaterThanOrEqualTo(savedMemberB.getAccount().getEarningRate());
    }
}
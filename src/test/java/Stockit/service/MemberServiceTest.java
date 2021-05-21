package Stockit.service;

import Stockit.domain.Member;
import Stockit.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest //스프링부트 띄우고 테스트(@Autowired 사용 위해)
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    public void 회원가입() {
        //given
        Member member = new Member();
        member.setName("회원1");
        member.setNickname("주린이1");
        member.setEmail("stockit@stockit.com");
        member.setPassword("abcdefg");

        //when
        Long saveId = memberService.join(member);

        //then
        Assertions.assertThat(member).isEqualTo(memberRepository.findOne(saveId));
    }

    @Test
    public void 중복회원_예외() {
        //given
        Member member = new Member();
        member.setName("회원1");
        member.setNickname("주린이1");
        member.setEmail("stockit@stockit.com");
        member.setPassword("abcdefg");

        Member member2 = new Member();
        member2.setName("회원1");
        member2.setNickname("주린이1");
        member2.setEmail("stockit@stockit.com");
        member2.setPassword("abcdefg");

        //when
        memberService.join(member);

        //then
        assertThrows(IllegalStateException.class, () -> memberService.join(member2));
    }

}
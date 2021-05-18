package Stockit.service;

import Stockit.AppConfig;
import Stockit.domain.Member;
import Stockit.repository.MemberRepository;
import Stockit.repository.MemberRepositoryImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberServiceTest {

    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
    MemberService memberService;
    MemberRepository repository;

    @BeforeEach
    public void beforeEach() {
        memberService = applicationContext.getBean("memberService", MemberService.class);
        repository = applicationContext.getBean("memberRepository", MemberRepositoryImpl.class);
    }

    @AfterEach
    public void afterEach() {
        repository.clearStore();
    }

    @Test
    public void 회원가입() {
        //given
        Member member = new Member("회원1", "주린이1", "stockit@stockit.com", "abcdefg");

        //when
        Long saveId = memberService.join(member);

        //then
        Member findMember = memberService.findOne(saveId).get();
        assertThat(findMember.getId()).isEqualTo(saveId);
    }

    @Test
    public void 중복회원_처리() {
        //given
        Member member = new Member("회원1", "주린이1", "stockit@stockit.com", "abcdefg");
        Member member2 = new Member("회원1", "주린이1", "stockit@stockit.com", "abcdefg");

        //when
        Long saveId = memberService.join(member);
        assertThrows(IllegalStateException.class, () -> memberService.join(member2));
    }

    @Test
    public void 전체회원_조회() {
        //given
        Member member = new Member("회원1", "주린이1", "stockit@stockit.com", "abcdefg");
        Member member2 = new Member("회원2", "주린이2", "stockit2@stockit.com", "abcdefg2");
        memberService.join(member);
        memberService.join(member2);

        //when
        List<Member> members = memberService.findMembers();

        //then
        assertThat(members.size()).isEqualTo(2);
    }

    @Test
    public void 단일회원_조회() {
        //given
        Member member = new Member("회원1", "주린이1", "stockit@stockit.com", "abcdefg");
        memberService.join(member);

        //when
        Member findMember = memberService.findOne(member.getId()).get();

        //then
        assertThat(findMember).isEqualTo(member);
    }

    @Test
    public void 비밀번호_변경() {
        //given
        Member member = new Member("회원1", "주린이1", "stockit@stockit.com", "abcdefg");
        memberService.join(member);

        //when
        Long changedPasswordMemberId = memberService.changePassword(member.getId(), "gfedcba");

        //then
        assertThat(changedPasswordMemberId).isEqualTo(member.getId());

    }
}
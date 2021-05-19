package Stockit.repository;

import Stockit.AppConfig;
import Stockit.domain.Member;
import Stockit.service.MemberService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class MemoryMemberRepositoryImplTest {

    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
    MemberService memberService;
    MemberRepository memberRepository;

    @BeforeEach
    public void beforeEach() {
        memberService = applicationContext.getBean("memberService", MemberService.class);
        memberRepository = applicationContext.getBean("memberRepository", MemoryMemberRepository.class);
    }

    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    public void 회원저장_AND_id로_조회() {
        //given
        Member member = new Member("회원1", "주린이1", "stockit@stockit.com", "abcdefg");

        //when
        memberRepository.save(member);

        //then
        Member findMember = memberRepository.findById(member.getId()).get();
        assertThat(findMember).isEqualTo(member);
    }

    @Test
    public void 이름으로_조회() {
        //given
        Member member = new Member("회원1", "주린이1", "stockit@stockit.com", "abcdefg");
        Member member2 = new Member("회원2", "주린이2", "stockit2@stockit.com", "abcdefg2");

        memberRepository.save(member);
        memberRepository.save(member2);

        //when
        Member findMember = memberRepository.findByName("회원1").get();

        //then
        assertThat(findMember).isEqualTo(member);
    }

    @Test
    public void 별명으로_조회() {
        //given
        Member member = new Member("회원1", "주린이1", "stockit@stockit.com", "abcdefg");
        Member member2 = new Member("회원2", "주린이2", "stockit2@stockit.com", "abcdefg2");

        memberRepository.save(member);
        memberRepository.save(member2);

        //when
        Member findMember = memberRepository.findByNickname("주린이1").get();

        //then
        assertThat(findMember).isEqualTo(member);
    }

    @Test
    public void 이메일로_조회() {
        //given
        Member member = new Member("회원1", "주린이1", "stockit@stockit.com", "abcdefg");
        Member member2 = new Member("회원2", "주린이2", "stockit2@stockit.com", "abcdefg2");

        memberRepository.save(member);
        memberRepository.save(member2);

        //when
        Member findMember = memberRepository.findByEmail("stockit@stockit.com").get();

        //then
        assertThat(findMember).isEqualTo(member);
    }

    @Test
    public void 전체회원_조회() {
        //given
        Member member = new Member("회원1", "주린이1", "stockit@stockit.com", "abcdefg");
        Member member2 = new Member("회원2", "주린이2", "stockit2@stockit.com", "abcdefg2");

        memberRepository.save(member);
        memberRepository.save(member2);

        //when
        List<Member> memberList = memberRepository.findAll();

        //then
        assertThat(memberList.size()).isEqualTo(2);
    }

}
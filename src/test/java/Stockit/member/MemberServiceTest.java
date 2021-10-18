package Stockit.member;

import Stockit.member.domain.Member;
import Stockit.member.dto.MemberInfo;
import Stockit.member.repository.MemberRepository;
import Stockit.member.service.MemberService;
import javassist.NotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

@SpringBootTest
@TestPropertySource("classpath:application-test.properties")
class MemberServiceTest {

    @Autowired private MemberService memberService;
    @Autowired private MemberRepository memberRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    private Member member;
    private Member member2;

    @BeforeEach
    public void 기본_설정() {
        memberRepository.deleteAll();
        member = new Member("회원1", "주린이1", "stockit@stockit.com", passwordEncoder.encode("abcdefg"));
        member2 = new Member("회원2", "주린이2", "stockit2@stockit.com", passwordEncoder.encode("abcdefg2"));
        memberService.join(member);
    }

    @Test
    public void 모든_멤버_조회() {
        //given
        memberService.join(member2);

        //when
        final List<MemberInfo> memberList = memberService.findAllMembers();

        //then
        Assertions.assertThat(memberList.size()).isEqualTo(2);
    }

    @Test
    public void 멤버_생성() throws NotFoundException {
        //given

        //when

        //then

    }

    @Test
    public void 닉네임_중복검사() {
        //given

        //when
        final boolean duplicatedNickname = memberService.findDuplicatedNickname("주린이1");
        final boolean notDuplicatedNickname = memberService.findDuplicatedNickname("new_주린이");

        //then
        Assertions.assertThat(duplicatedNickname).isEqualTo(true);
        Assertions.assertThat(notDuplicatedNickname).isEqualTo(false);

    }

    @Test
    public void 이메일_중복검사() {
        //given

        //when
        final boolean duplicatedEmail = memberService.findDuplicatedEmail("stockit@stockit.com");
        final boolean notDuplicatedEmail = memberService.findDuplicatedEmail("new-stockit@stockit.com");

        //then
        Assertions.assertThat(duplicatedEmail).isEqualTo(true);
        Assertions.assertThat(notDuplicatedEmail).isEqualTo(false);

    }

    @Test
    public void 랭킹조회() {

    }

    @Test
    public void 로그인() {
        //given

        //when


        //then
    }

    @Test
    public void 주문조회() {

    }
}
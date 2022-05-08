package Stockit.infrastructure.member;

import Stockit.domain.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MemberReaderImplTest {

    @Mock
    MemberRepository memberRepository;

    @InjectMocks
    MemberReaderImpl memberReader;

    @Test
    void 존재하는_멤버_idx를_통해_멤버를_찾을_수_있다() {
        Member member = Mockito.mock(Member.class);
        when(memberRepository.findById(1L))
                .thenReturn(Optional.ofNullable(member));

        Optional<Member> actualMember = memberReader.findMember(1L);

        Assertions.assertThat(actualMember)
                .isEqualTo(Optional.ofNullable(member));
    }

    @Test
    void 존재하지_않는_멤버_idx를_통해_멤버를_찾을_수_있다() {
        when(memberRepository.findById(1L))
                .thenReturn(Optional.empty());

        Optional<Member> actualMember = memberReader.findMember(1L);

        Assertions.assertThat(actualMember)
                .isEqualTo(Optional.empty());
    }

    @Test
    void 멤버_전체_조회를_할_수_있다() {
        List<Member> memberList = List.of();
        when(memberRepository.findAll())
                .thenReturn(memberList);

        List<Member> actualMemberList = memberReader.getAllActiveMembers();

        Assertions.assertThat(actualMemberList)
                .isEqualTo(memberList);
    }

    @Test
    void 존재하는_닉네임으로_멤버를_조회할_수_있다() {
        String givenNickname = "gildong";
        Member mockMember = Mockito.mock(Member.class);
        when(mockMember.getNickname())
                .thenReturn(givenNickname);
        when(memberRepository.findByNickname(givenNickname))
                .thenReturn(Optional.of(mockMember));

        Assertions.assertThat(memberReader.findMemberByNickname(givenNickname))
                .isEqualTo(Optional.of(mockMember));
        Assertions.assertThat(memberReader.findMemberByNickname(givenNickname).orElseThrow().getNickname())
                .isEqualTo(givenNickname);
    }

    @Test
    void 존재하지_않는_닉네임으로_멤버를_조회하면_empty가_반환된다() {
        when(memberRepository.findByNickname("unknown"))
                .thenReturn(Optional.empty());

        Assertions.assertThat(memberReader.findMemberByNickname("unknown"))
                .isEqualTo(Optional.empty());
    }

    @Test
    void 존재하는_이메일로_멤버를_조회할_수_있다() {
        String givenEmail = "test@test.com";
        Member mockMember = Mockito.mock(Member.class);
        when(mockMember.getNickname())
                .thenReturn(givenEmail);
        when(memberRepository.findByEmail(givenEmail))
                .thenReturn(Optional.of(mockMember));

        Assertions.assertThat(memberReader.findMemberByEmail(givenEmail))
                .isEqualTo(Optional.of(mockMember));
        Assertions.assertThat(memberReader.findMemberByEmail(givenEmail).orElseThrow().getNickname())
                .isEqualTo(givenEmail);
    }

    @Test
    void 존재하지_않는_이메일로_멤버를_조회하면_empty가_반환된다() {
        when(memberRepository.findByEmail("unknown"))
                .thenReturn(Optional.empty());

        Assertions.assertThat(memberReader.findMemberByEmail("unknown"))
                .isEqualTo(Optional.empty());
    }

    @Test
    void 랭킹순으로_멤버를_조회할_수_있다() {
        Member firstMember = Mockito.mock(Member.class);
        Member secondMember = Mockito.mock(Member.class);
        when(memberReader.getAllByOrderByEarningRateDesc()).thenReturn(List.of(firstMember, secondMember));

        List<Member> rankingList = memberReader.getAllByOrderByEarningRateDesc();

        // earningRate 내림차순 여부는 레포지토리단에서 검증
        Assertions.assertThat(rankingList).hasSize(2);
        Assertions.assertThat(rankingList).containsExactly(firstMember, secondMember);
    }
}
package Stockit.member.controller;

import Stockit.member.domain.Member;
import Stockit.member.dto.MemberDto;
import Stockit.member.service.MemberService;
import Stockit.ranking.domain.Rank;
import Stockit.ranking.service.RankService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Calendar;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private RankService rankService;

    @GetMapping(value = "/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberDto", new MemberDto());
        return "members/memberJoin";
    }

    @PostMapping(value = "/members/new")
    public String create(MemberDto form, BindingResult result) {
        if(result.hasErrors()) {
            return "members/memberJoin";
        }

        Member member = new Member();
        member.setName(form.getName());
        member.setNickname(form.getNickname());
        member.setEmail(form.getEmail());
        member.setPassword(form.getPassword());

        //랭킹 데이터도 같이 만들어
        //처음 회원가입할때 한번만 적용됨
        Rank rank = new Rank(member.getNickname(), 0.0,(long)1000000);

        member.setRank(rank);
        rank.setMember(member);

        memberService.join(member);

        return "redirect:/";
    }

    @GetMapping(value = "/members")
    public String list(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}

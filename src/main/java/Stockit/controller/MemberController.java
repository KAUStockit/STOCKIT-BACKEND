package Stockit.controller;

import Stockit.domain.Member;
import Stockit.dto.MemberForm;
import Stockit.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/members/")
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/new")
    public String createForm() {
        return "members/createMemberForm";
    }

    @PostMapping("/new")
    public String create(MemberForm form) {
        if(form.getName().equals("") || form.getNickname().equals("") || form.getEmail().equals("") || form.getPassword().equals("")) return "redirect:/members/new";
        Member member = form.toMember();
        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping("/memberList")
    public String allMembers(Model model) {
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        System.out.println(members);

        return "members/memberList";
    }
}

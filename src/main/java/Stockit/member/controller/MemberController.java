package Stockit.member.controller;

import Stockit.member.domain.Member;
import Stockit.member.dto.MemberDto;
import Stockit.member.dto.RankDto;
import Stockit.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping(value = "/new")
    public void createForm(Model model) {
        log.info("member join form");
    }

    @GetMapping(value = "/")
    public ResponseEntity<List<Member>> list() {
        List<Member> members = memberService.findAllMembers();
        return ResponseEntity.status(HttpStatus.OK).body(members);
    }

    @PostMapping(value = "/new")
    public ResponseEntity<Long> create(@RequestBody MemberDto form) {
        Member member = new Member(form);
        memberService.join(member);
        return ResponseEntity.status(HttpStatus.OK).body(member.getIdx());
    }

    @GetMapping(value = "/rank_list")
    public ResponseEntity<List<RankDto>> getRankList() {
        List<RankDto> rankList = memberService.getRankList();
        return ResponseEntity.status(HttpStatus.OK).body(rankList);
    }

    @PutMapping(value = "/rank_update")
    public void rankUpdate() {
        memberService.updateEarningRate();
    }


}

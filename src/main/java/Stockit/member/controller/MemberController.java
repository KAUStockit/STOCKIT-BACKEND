package Stockit.member.controller;

import Stockit.member.domain.Member;
import Stockit.member.dto.MemberDto;
import Stockit.member.dto.RankDto;
import Stockit.member.service.MemberService;
import Stockit.member.vo.LoginVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping(value = "/")
    public ResponseEntity<List<Member>> list() {
        List<Member> members = memberService.findAllMembers();
        return ResponseEntity.status(HttpStatus.OK).body(members);
    }

    @GetMapping(value = "/new")
    public void createForm(Model model) {
        log.info("member join form");
    }

    @PostMapping(value = "/new")
    public ResponseEntity<Long> create(@RequestBody MemberDto form) {
        Member member = new Member(form);
        memberService.join(member);
        return ResponseEntity.status(HttpStatus.OK).body(member.getIdx());
    }

//    @PostMapping(value = "/login")
//    public ResponseEntity<String> login(@RequestBody LoginVO loginVO) {
//        String memberId = loginVO.getEmail();
//        String memberPassword = loginVO.getPassword();
//        String token = memberService.login(memberId, memberPassword);
//        return ResponseEntity.status(HttpStatus.OK).body(token);
//    }

    @PostMapping(value = "/validate/nickname")
    public ResponseEntity<String> checkDuplicatedNickname(@RequestBody Map<String, String> nicknameMap) {
        boolean validate = memberService.findDuplicatedNickname(nicknameMap.get("nickname"));
        if (validate) return ResponseEntity.status(HttpStatus.CONFLICT).body("닉네임 중복검사 불통과");
        else return ResponseEntity.status(HttpStatus.OK).body("닉네임 중복검사 통과");
    }

    @PostMapping(value = "/validate/email")
    public ResponseEntity<String> checkDuplicatedEmail(@RequestBody Map<String,String> emailMap) {
        boolean validate = memberService.findDuplicatedEmail(emailMap.get("email"));
        if (validate) return ResponseEntity.status(HttpStatus.CONFLICT).body("닉네임 중복검사 불통과");
        else return ResponseEntity.status(HttpStatus.OK).body("이메일 중복검사 통과");
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

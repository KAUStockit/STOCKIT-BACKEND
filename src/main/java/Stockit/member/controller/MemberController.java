package Stockit.member.controller;

import Stockit.member.domain.Member;
import Stockit.member.dto.MemberDto;
import Stockit.member.service.MemberService;
import Stockit.member.vo.AuthRequest;
import Stockit.member.vo.RankVO;
import Stockit.member.vo.UserInfo;
import Stockit.response.BasicResponse;
import Stockit.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    //모든 멤버 조회
    @GetMapping(value = "/list")
    public ResponseEntity<BasicResponse> list() {
        List<Member> members = memberService.findAllMembers();
        final List<UserInfo> userInfoList = members.stream().map(UserInfo::new).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(
                new SuccessResponse<>(HttpStatus.OK.value(), "멤버 리스트", userInfoList));
    }

    //멤버 생성
    @PostMapping(value = "/new")
    public ResponseEntity<BasicResponse> create(@RequestBody MemberDto form) {
        Member member;
        try {
            form.setPassword(passwordEncoder.encode(form.getPassword()));
            member = new Member(form);
            memberService.join(member);
        } catch (Exception e) {
            throw new IllegalStateException("멤버를 생성하지 못했습니다.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new SuccessResponse<>(HttpStatus.OK.value(), "회원가입 성공", member.getIdx()));
    }

    //닉네임 중복 검사
    @GetMapping(value = "/login/validate/nickname")
    public ResponseEntity<BasicResponse> checkDuplicatedNickname(@RequestParam String nickname) {
        boolean duplicated = memberService.findDuplicatedNickname(nickname);
        if (duplicated) throw new IllegalArgumentException("닉네임 중복");
        else return ResponseEntity.status(HttpStatus.OK).body(
                new SuccessResponse<>(HttpStatus.OK.value(), "닉네임 중복검사 통과", nickname));
    }

    //이메일 중복 검사
    @GetMapping(value = "/login/validate/email")
    public ResponseEntity<BasicResponse> checkDuplicatedEmail(@RequestParam String email) {
        boolean duplicated = memberService.findDuplicatedEmail(email);
        if (duplicated) throw new IllegalArgumentException("이메일 중복");
        else return ResponseEntity.status(HttpStatus.OK).body(
                new SuccessResponse<>(HttpStatus.OK.value(), "이메일 중복검사 통과", email));
    }

    //랭킹 조회
    @GetMapping(value = "/rank_list")
    public ResponseEntity<BasicResponse> getRankList() {
        List<RankVO> rankList = memberService.getRankList();
        return ResponseEntity.status(HttpStatus.OK).body(
                new SuccessResponse<>(HttpStatus.OK.value(), "랭킹 조회", rankList));
    }


    //로그인
    @PostMapping(value = "/login")
    public ResponseEntity<BasicResponse> login(@RequestBody AuthRequest authRequest) {
        final UserInfo userInfo = memberService.login(authRequest);
        return ResponseEntity.status(HttpStatus.OK).body(
                new SuccessResponse<>(HttpStatus.OK.value(), "로그인 성공", userInfo));
    }

    //주문 조회
    @GetMapping(value = "/{memberIdx}/orders")
    public ResponseEntity<BasicResponse> getOrders(@PathVariable Long memberIdx) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new SuccessResponse<>(HttpStatus.OK.value(), "주문 조회", memberService.findAllOrders(memberIdx)));
    }
}

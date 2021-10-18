package Stockit.member.controller;

import Stockit.member.domain.Member;
import Stockit.member.dto.*;
import Stockit.member.service.MemberService;
import Stockit.order.dto.OrderInfo;
import Stockit.response.BasicResponse;
import Stockit.response.SuccessResponse;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        final List<MemberInfo> members = memberService.findAllMembers();
        return ResponseEntity.status(HttpStatus.OK).body(
                new SuccessResponse<>(HttpStatus.OK.value(), "멤버 리스트", members));
    }

    //멤버 생성
    @PostMapping(value = "/new")
    public ResponseEntity<BasicResponse> create(@RequestBody MemberJoinRequest form) {
        form.setPassword(passwordEncoder.encode(form.getPassword()));
        Member member = new Member(form);
        final Long memberId = memberService.join(member);
        return ResponseEntity.status(HttpStatus.OK).body(
                new SuccessResponse<>(HttpStatus.OK.value(), "회원가입 성공", memberId));
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
    @GetMapping(value = "/rank")
    public ResponseEntity<BasicResponse> getRankList() {
        List<RankingInfo> rankList = memberService.getRankList();
        return ResponseEntity.status(HttpStatus.OK).body(
                new SuccessResponse<>(HttpStatus.OK.value(), "랭킹 조회", rankList));
    }


    //로그인
    @PostMapping(value = "/login")
    public ResponseEntity<BasicResponse> login(@RequestBody LoginRequest loginRequest) {
        final MemberInfo memberInfo = memberService.login(loginRequest);
        return ResponseEntity.status(HttpStatus.OK).body(
                new SuccessResponse<>(HttpStatus.OK.value(), "로그인 성공", memberInfo));
    }

    //주문 조회
    @GetMapping(value = "/{memberId}/orders")
    public ResponseEntity<BasicResponse> getOrders(@PathVariable Long memberId) throws NotFoundException {
        final MemberInfo memberInfo = memberService.findMember(memberId);
        final List<OrderInfo> orderInfoList = memberInfo.getOrders();
        return ResponseEntity.status(HttpStatus.OK).body(
                new SuccessResponse<>(HttpStatus.OK.value(), "주문 조회", orderInfoList));
    }

    //보유 주식 조회
    @GetMapping(value = "/{memberId}/stocks")
    public ResponseEntity<BasicResponse> getMyStocks(@PathVariable Long memberId) throws NotFoundException {
        final MemberInfo memberInfo = memberService.findMember(memberId);
        final List<AccountStockInfo> stockInfoList = memberInfo.getStocks();
        return ResponseEntity.status(HttpStatus.OK).body(
                new SuccessResponse<>(HttpStatus.OK.value(), "보유 주식 조회", stockInfoList));
    }
}

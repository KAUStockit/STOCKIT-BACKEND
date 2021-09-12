package Stockit.member.controller;

import Stockit.exception.LoginFailedException;
import Stockit.jwt.token.JwtAuthToken;
import Stockit.member.domain.Member;
import Stockit.member.dto.MemberDto;
import Stockit.member.service.MemberService;
import Stockit.member.vo.AuthRequest;
import Stockit.member.vo.RankVO;
import Stockit.member.vo.UserInfo;
import Stockit.order.domain.Order;
import Stockit.response.BasicResponse;
import Stockit.response.ErrorResponse;
import Stockit.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    //모든 멤버 조회
    @GetMapping(value = "/")
    public ResponseEntity<List<Member>> list() {
        List<Member> members = memberService.findAllMembers();
        return ResponseEntity.status(HttpStatus.OK).body(members);
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
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(),"중복검사를 통과하지 못했습니다."));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<>(HttpStatus.OK.value(), "회원가입 성공했습니다.", member.getIdx()));
    }

    //닉네임 중복 검사
    @GetMapping(value = "/login/validate/nickname")
    public ResponseEntity<String> checkDuplicatedNickname(@RequestParam String nickname) {
        boolean validate = memberService.findDuplicatedNickname(nickname);
        if (validate) return ResponseEntity.status(HttpStatus.CONFLICT).body("닉네임 중복검사 불통과");
        else return ResponseEntity.status(HttpStatus.OK).body("닉네임 중복검사 통과");
    }

    //이메일 중복 검사
    @GetMapping(value = "/login/validate/email")
    public ResponseEntity<String> checkDuplicatedEmail(@RequestParam String email) {
        boolean validate = memberService.findDuplicatedEmail(email);
        if (validate) return ResponseEntity.status(HttpStatus.CONFLICT).body("닉네임 중복검사 불통과");
        else return ResponseEntity.status(HttpStatus.OK).body("이메일 중복검사 통과");
    }

    //랭킹 조회
    @GetMapping(value = "/rank_list")
    public ResponseEntity<List<RankVO>> getRankList() {
        List<RankVO> rankList = memberService.getRankList();
        return ResponseEntity.status(HttpStatus.OK).body(rankList);
    }


    //로그인
    @PostMapping(value = "/login")
    public ResponseEntity<BasicResponse> login(@RequestBody AuthRequest authRequest) {
        final Optional<Member> optionalMember = memberService.login(authRequest);
        if (optionalMember.isPresent()) {
            JwtAuthToken jwtAuthToken = (JwtAuthToken) memberService.createAuthToken(authRequest.getEmail(), optionalMember.get().getRole());
            return ResponseEntity.status(HttpStatus.OK).body(
                    new SuccessResponse<>(HttpStatus.OK.value(), "로그인 성공", new UserInfo(optionalMember.get(), jwtAuthToken.getToken())));
        } else {
            throw new LoginFailedException();
        }
    }

    //주문 조회
    @GetMapping(value = "/{memberIdx}/orders")
    public ResponseEntity<List<Order>> getOrders(@PathVariable Long memberIdx) {
        return ResponseEntity.status(HttpStatus.OK).body(memberService.findAllOrders(memberIdx));
    }
}

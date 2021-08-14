package Stockit.member.controller;

import Stockit.member.domain.Member;
import Stockit.member.dto.MemberDto;
import Stockit.member.dto.RankDto;
import Stockit.member.service.MemberService;
import Stockit.member.vo.AuthRequest;
import Stockit.member.vo.UserInfo;
import Stockit.response.BasicResponse;
import Stockit.response.ErrorResponse;
import Stockit.response.SuccessResponse;
import Stockit.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

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
    public ResponseEntity<BasicResponse> create(@RequestBody MemberDto form) {
        Member member;
        try {
            member = new Member(form);
            memberService.join(member);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("중복검사를 통과하지 못했습니다."));
        }
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<>(member.getIdx(), "회원가입 성공했습니다."));
    }

    @PostMapping(value = "/login/validate/nickname")
    public ResponseEntity<String> checkDuplicatedNickname(@RequestBody Map<String, String> nicknameMap) {
        boolean validate = memberService.findDuplicatedNickname(nicknameMap.get("nickname"));
        if (validate) return ResponseEntity.status(HttpStatus.CONFLICT).body("닉네임 중복검사 불통과");
        else return ResponseEntity.status(HttpStatus.OK).body("닉네임 중복검사 통과");
    }

    @PostMapping(value = "/login/validate/email")
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

    @PostMapping(value = "/login")
    public ResponseEntity<BasicResponse> login(@RequestBody AuthRequest authRequest) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), Member.sha256(authRequest.getPassword())));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("로그인에 실패했습니다."));
        }
        Member member = memberService.findMemberInfo(authRequest.getEmail()).orElseThrow(() -> new NullPointerException("회원 정보가 없습니다."));
        String generateToken = jwtUtil.generateToken(authRequest.getEmail());
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<>(new UserInfo(member, generateToken),"로그인에 성공했습니다."));
    }
}

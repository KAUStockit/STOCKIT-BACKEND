package Stockit.interfaces.member;

import Stockit.common.response.CommonResponse;
import Stockit.domain.member.MemberService;
import Stockit.domain.member.dto.MemberInfo;
import Stockit.domain.member.dto.RankingInfo;
import Stockit.domain.stock.StockService;
import Stockit.interfaces.member.dto.*;
import Stockit.interfaces.order.dto.OrderInfo;
import Stockit.interfaces.stock.dto.StockInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;
    private final StockService stockService;

    //모든 멤버 조회
    @GetMapping
    public CommonResponse<List<MemberInfo>> list() {
        return CommonResponse.success(memberService.getAllMembers());
    }

    //멤버 생성
    @PostMapping
    public CommonResponse<Long> create(@RequestBody MemberJoinRequest form) {
//        form.encodePassword(passwordEncoder.encode(form.getPassword()));
        return CommonResponse.success(memberService.join(form));
    }

    //닉네임 중복 검사
    @GetMapping(value = "/login/valid-nickname")
    public CommonResponse<String> checkDuplicatedNickname(@RequestParam String nickname) {
        boolean duplicated = memberService.isDuplicatedNickname(nickname);
        if (duplicated) throw new IllegalArgumentException("닉네임 중복");
        return CommonResponse.success(nickname);
    }

    //이메일 중복 검사
    @GetMapping(value = "/login/valid-email")
    public CommonResponse<String> checkDuplicatedEmail(@RequestParam String email) {
        boolean duplicated = memberService.isDuplicatedEmail(email);
        if (duplicated) throw new IllegalArgumentException("이메일 중복");
        return CommonResponse.success(email);
    }

    //랭킹 조회
    @GetMapping(value = "/rank")
    public CommonResponse<List<RankingInfo>> getRankList() {
        return CommonResponse.success(memberService.getRankingList());
    }


    //로그인
    @PostMapping(value = "/login")
    public CommonResponse<MemberInfo> login(@RequestBody LoginRequest loginRequest) {
        return CommonResponse.success(memberService.login(loginRequest));
    }

    //주문 조회
    @GetMapping(value = "/{memberId}/orders")
    public CommonResponse<List<OrderInfo>> getOrders(@PathVariable Long memberId) {
        final MemberInfo memberInfo = memberService.getMember(memberId);
        return CommonResponse.success(memberInfo.getOrders());
    }

    //보유 주식 조회
    @GetMapping(value = "/{memberId}/stocks")
    public CommonResponse<Pair<List<AccountStockInfo>, List<StockInfo>>> getMyStocks(@PathVariable Long memberId) {
        final MemberInfo memberInfo = memberService.getMember(memberId);
        final List<Long> stockCodeList =
                memberInfo.getStocks().stream().map(AccountStockInfo::getStockCode).collect(Collectors.toList());
        final List<StockInfo> stockInfoList = stockService.findStockList(stockCodeList);
        Pair<List<AccountStockInfo>, List<StockInfo>> myStockInfoList = Pair.of(memberInfo.getStocks(), stockInfoList);
        return CommonResponse.success(myStockInfoList);
    }
}

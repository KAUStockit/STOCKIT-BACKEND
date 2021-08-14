package Stockit.member.vo;

import Stockit.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class UserInfo {
    private final long memberIdx;
    private final String name;
    private final String nickname;
    private final String email;
    private final int balance;
    private final int beforeBalance;
    private final LocalDateTime createdTime;
    private final double earningRate;
    private final String role;
    private final String token;

    public UserInfo(Member member, String token) {
        this(member.getIdx(), member.getName(), member.getNickname(), member.getEmail(),
                member.getBalance(), member.getBeforeBalance(), member.getCreatedTime(),
                member.getEarningRate(), member.getRole(), token);
    }
}

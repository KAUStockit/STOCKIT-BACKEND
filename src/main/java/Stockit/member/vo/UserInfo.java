package Stockit.member.vo;

import Stockit.member.domain.Member;
import Stockit.member.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

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
    private final Role role;
    private final String token;

    public UserInfo(Member member, String token) {
        this(member.getIdx(), member.getName(), member.getNickname(), member.getEmail(),
                member.getAccount().getBalance(), member.getAccount().getBeforeBalance(), member.getCreatedTime().truncatedTo(ChronoUnit.MILLIS),
                member.getAccount().getEarningRate(), member.getRole(), token);
    }

    public UserInfo(Member member) {
        this(member.getIdx(), member.getName(), member.getNickname(), member.getEmail(),
                member.getAccount().getBalance(), member.getAccount().getBeforeBalance(), member.getCreatedTime(),
                member.getAccount().getEarningRate(), member.getRole(), "");
    }
}

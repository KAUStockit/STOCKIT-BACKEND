package Stockit.interfaces.member;

import Stockit.domain.member.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class RankingInfo {
    private final String email;
    private final String nickname;
    private final String name;
    private final Double earningRate;

    public RankingInfo(Member member) {
        this.email = member.getEmail();
        this.nickname = member.getNickname();
        this.name = member.getName();
        this.earningRate = member.getAccount().getEarningRate();
    }
}

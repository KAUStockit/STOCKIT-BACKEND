package Stockit.member.vo;

import Stockit.member.domain.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class RankVO {
    private final String email;
    private final String nickname;
    private final String name;
    private final Double earningRate;

    public RankVO(Member member) {
        this.email = member.getEmail();
        this.nickname = member.getNickname();
        this.name = member.getName();
        this.earningRate = member.getAccount().getEarningRate();
    }
}

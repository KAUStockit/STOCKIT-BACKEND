package Stockit.ranking.domain;

import Stockit.member.domain.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Rank {
    @Id
    @Column(name = "nickname")
    private String nickname;

    @Column(name = "seven_days_balance")
    private Long sevenDaysBalance;

    @Column(name = "earning_rate")
    private Double earningRate;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member ;

    @Builder
    public Rank(String nickname, Double earningRate, Long sevenDaysBalance) {
        this.nickname = nickname;
        this.earningRate = earningRate;
        this.sevenDaysBalance = sevenDaysBalance;

    }

    public Rank() {

    }
}

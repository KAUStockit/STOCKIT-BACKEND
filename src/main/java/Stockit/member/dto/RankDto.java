package Stockit.member.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class RankDto {
    String email;
    String nickname;
    Double earningRate;

    public RankDto(String email, String nickname, Double earningRate) {
        this.email = email;
        this.nickname = nickname;
        this.earningRate = earningRate;
    }
}

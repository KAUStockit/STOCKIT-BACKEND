package Stockit.ranking.dto;

import lombok.Getter;

//Ranking조회시 사용되는 Dto
@Getter
public class RankResponseDto {
    private Long id;
    private String nickname;
    private Double earningRate;
}

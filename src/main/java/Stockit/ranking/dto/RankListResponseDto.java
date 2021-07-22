package Stockit.ranking.dto;

import Stockit.notice.domain.posts.Posts;
import Stockit.ranking.domain.Rank;

public class RankListResponseDto {
    private String nickname;
    private Double earningRate;

    public RankListResponseDto(Rank entity){
        this.nickname = entity.getNickname();
        this.earningRate = entity.getEarningRate();
    }
}

package Stockit.ranking.controller;

import Stockit.notice.dto.PostsResponseDto;
import Stockit.ranking.dto.RankListResponseDto;
import Stockit.ranking.dto.RankResponseDto;
import Stockit.ranking.service.RankService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RankApiController {
    private RankService rankService;

    //ranking조회
    @GetMapping("/api/rank/list")
    public List<RankListResponseDto> findAll() {
        return rankService.rankSearch();
    }

}

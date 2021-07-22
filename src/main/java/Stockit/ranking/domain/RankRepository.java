package Stockit.ranking.domain;

import Stockit.notice.domain.posts.Posts;
import Stockit.ranking.dto.RankResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RankRepository extends JpaRepository<Rank, Long> {

    //조회를 위한 Query 작성
    //쿼리를 따로 작성해서 수익률을 따져서 내림차순으로 정리함
    @Query("select r from Rank r ORDER BY r.earningRate desc")
    List<Rank> findAllEarningRateDesc();
}

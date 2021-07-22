package Stockit.ranking.service;

import Stockit.member.repository.MemberRepository;
import Stockit.ranking.domain.RankRepository;
import Stockit.ranking.dto.RankListResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RankService {

    private RankRepository rankRepository;

    //Ranking 조회하고 뿌려주기
    @Transactional
    public List<RankListResponseDto> rankSearch(){

        return rankRepository.findAllEarningRateDesc().stream()
                .map(RankListResponseDto::new)
                .collect(Collectors.toList());
    }

    //Ranking 7일마다 DB에 반영하기
    @Transactional
    public void /*List<RankRepository>*/ sevenDaysBalanceUpdate(){

    }
    //Ranking 수익률 계산하고 업데이트 시키기
    @Transactional
    public void /*List<RankRepository>*/ earningRateUpdate(){

    }
}

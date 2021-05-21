package Stockit.repository;

import Stockit.domain.Stock;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class StockRepository {

    private final EntityManager em;

    public void save(Stock stock) {
        if(stock.getCode() == null) { //신규등록
            em.persist(stock);
        } else { //업데이트
           em.merge(stock);
        }
    }

    public Stock findOne(String stockCode) {
        return em.find(Stock.class, stockCode);
    }

    public List<Stock> findAll() {
        return em.createQuery("select i from Stock i", Stock.class).getResultList();
    }
}

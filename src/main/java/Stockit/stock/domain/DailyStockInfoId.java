package Stockit.stock.domain;

import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;

@Embeddable
@NoArgsConstructor
public class DailyStockInfoId implements Serializable {

    private Long stockCode;
    private LocalDate date = LocalDate.now();

    public DailyStockInfoId(Long stockCode) {
        this.stockCode = stockCode;
    }
}

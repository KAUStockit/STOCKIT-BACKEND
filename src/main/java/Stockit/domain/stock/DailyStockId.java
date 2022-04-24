package Stockit.domain.stock;

import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;

@Embeddable
@NoArgsConstructor
public class DailyStockId implements Serializable {

    private Long stockCode;

    @CreatedDate
    private LocalDate date;

    public DailyStockId(Long stockCode) {
        this.stockCode = stockCode;
    }
}

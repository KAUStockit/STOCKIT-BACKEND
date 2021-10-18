package Stockit.stock.domain;

import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Embeddable;
import javax.persistence.EntityListeners;
import java.io.Serializable;
import java.time.LocalDate;

@Embeddable
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class DailyStockId implements Serializable {

    private Long stockCode;

    @CreatedDate
    private LocalDate date;

    public DailyStockId(Long stockCode) {
        this.stockCode = stockCode;
    }
}

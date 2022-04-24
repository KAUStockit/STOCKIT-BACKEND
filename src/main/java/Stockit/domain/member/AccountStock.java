package Stockit.domain.member;

import Stockit.domain.stock.Stock;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
public class AccountStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ACCOUNT_STOCK_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ACCOUNT_ID")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "STOCK_CODE")
    private Stock stock;

    private int amount;

    public AccountStock(Account account, Stock stock, int amount) {
        this.account = account;
        this.stock = stock;
        this.amount = amount;
    }

    public void updateAmount(int updateAmount) {
        this.amount += updateAmount;
    }
}

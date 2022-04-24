package Stockit.domain.member;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

// Member와 1:1
// Stock과 N:M
@Entity
@Getter
@NoArgsConstructor
public class Account {

    private final int INITIAL_BALANCE = 1000000; // 첫 회원 잔고
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ACCOUNT_ID")
    private Long id;

    private Double earningRate = 0D;

    private Integer balance = INITIAL_BALANCE;

    //이전 주의 최종 balance
    private Integer beforeBalance = INITIAL_BALANCE;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<AccountStock> accountStocks = new ArrayList<>();

    public void updateBalance(int money) {
        this.balance = this.balance + money;
    }
}

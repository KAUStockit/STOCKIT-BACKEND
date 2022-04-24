package Stockit.interfaces.member;

import Stockit.domain.member.AccountStock;
import lombok.Getter;

@Getter
public class AccountStockInfo {

    private final Long stockCode;
    private final String stockName;
    private final int price;
    private final int amount;

    public AccountStockInfo(AccountStock accountStock) {
        this.stockCode = accountStock.getStock().getId();
        this.stockName = accountStock.getStock().getStockName();
        this.price = accountStock.getStock().getPrice();
        this.amount = accountStock.getAmount();
    }
}

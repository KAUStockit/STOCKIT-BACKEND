package Stockit.member.dto;

import Stockit.member.domain.AccountStock;
import lombok.Getter;

@Getter
public class AccountStockInfo {

    private String stockName;
    private int price;
    private int amount;

    public AccountStockInfo(AccountStock accountStock) {
        this.stockName = accountStock.getStock().getStockName();
        this.price = accountStock.getStock().getPrice();
        this.amount = accountStock.getAmount();
    }
}

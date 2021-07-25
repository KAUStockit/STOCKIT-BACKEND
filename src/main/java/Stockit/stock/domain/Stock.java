package Stockit.stock.domain;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Stock {
    @Id
    private String code;
    private String name;
    private int price;
    private boolean is_active; //거래 or 거래중지

    public Stock(String code, String name, int price, boolean is_active) {
        this.code = code;
        this.name = name;
        this.price = price;
        this.is_active = is_active;
    }

    @OneToMany(mappedBy = "stock")
    private List<AcceptedItem> acceptedItems = new ArrayList<>();
}
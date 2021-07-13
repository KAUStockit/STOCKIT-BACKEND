package Stockit.stock.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Stock {
    @Id
    private String code;
    private String name;
    private Long price;
    private boolean is_active; //거래 or 거래중지

    @OneToMany(mappedBy = "stock")
    private List<AcceptedItem> acceptedItems = new ArrayList<>();
}
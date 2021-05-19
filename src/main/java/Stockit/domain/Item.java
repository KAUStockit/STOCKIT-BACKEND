package Stockit.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Table(name = "Item")
@Entity
@Data
public class Item {
    // code -> varchar(45), name(company) -> varchar(45), is_active -> boolean, issuance->double, url->varchar(45)
    @Id
    private Long id;

    @Column(name = "code")
    private String code;
    @Column(name = "name")
    private String name;
    @Column(name = "is_active")
    private AtomicBoolean is_active;
    @Column(name = "issuance")
    private AtomicLong issuance;
    @Column(name = "url")
    private String url;

    public Item(String code,String name,AtomicBoolean is_active, AtomicLong issuance, String url){
        this.code = code;
        this.name = name;
        this.issuance = issuance;
        this.is_active = is_active;

        this.url = url;
    }

    public Item() {

    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
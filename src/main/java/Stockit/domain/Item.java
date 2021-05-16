package Stockit.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Data
@Table(name = "Item")
@Entity
public class Item {
    // code -> varchar(45), name(company) -> varchar(45), is_active -> boolean, issuance->double, url->varchar(45)
    @Id
    private AtomicInteger id;

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

    public Item(AtomicInteger id, String code, String name, AtomicBoolean is_active, AtomicLong issuance, String url) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.is_active = is_active;
        this.issuance = issuance;
        this.url = url;
    }

    public Item() {

    }

    public AtomicInteger getId() {
        return id;
    }

    public void setId(AtomicInteger id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AtomicBoolean getIs_active() {
        return is_active;
    }

    public void setIs_active(AtomicBoolean is_active) {
        this.is_active = is_active;
    }

    public AtomicLong getIssurance() {
        return issuance;
    }

    public void setIssurance(AtomicLong issurance) {
        this.issuance = issurance;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
package Stockit.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private final String name;
    private final String nickname;
    private final String email;
    private String password;
    @Column(columnDefinition = "long default 1000000") //기본값 100만원
    private Long balance;

    public Member(String name, String nickname, String email, String password) {
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
    }

    public Long setPassword(String password) {
        this.password = password;
        return getId();
    }

    public Long setId(Long id) {
        this.id = id;
        return getId();
    }
}

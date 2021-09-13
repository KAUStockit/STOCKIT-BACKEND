package Stockit.member.domain;

import Stockit.member.dto.MemberDto;
import Stockit.order.domain.Order;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
@Entity
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_idx")
    private Long idx;

    private String name;

    @Column(unique = true)
    private String nickname;

    @Column(unique = true)
    private String email;

    private String password;

    private int balance;

    //이전 주의 최종 balance
    private int beforeBalance;

    @CreatedDate
    private LocalDateTime createdTime;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    private Double earningRate;

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();
    
    private final int INITIAL_BALANCE = 1000000; // 첫 회원 잔고

    ////////////////////////////////////////////////////////////////////////////

    public Member(String name, String nickname, String email, String password) {
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.password = password;

        this.role = Role.USER; // 기본은 유저
        this.beforeBalance = INITIAL_BALANCE;
        this.balance = INITIAL_BALANCE;
        this.earningRate = 0d;
    }

    public Member(MemberDto memberDto) {
        this(memberDto.getName(), memberDto.getNickname(), memberDto.getEmail(), memberDto.getPassword());
    }
}

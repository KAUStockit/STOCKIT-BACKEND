package Stockit.domain.member;

import Stockit.domain.order.Order;
import Stockit.interfaces.member.dto.MemberJoinRequest;
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

    @OneToMany(mappedBy = "member")
    private final List<Order> orders = new ArrayList<>();
    @Enumerated(EnumType.STRING)
    private final Role role = Role.USER;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long id;
    private String name;
    @Column(unique = true)
    private String nickname;
    @Column(unique = true)
    private String email;
    private String password;
    @CreatedDate
    private LocalDateTime createdTime;
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ACCOUNT_ID")
    private Account account;

    ////////////////////////////////////////////////////////////////////////////

    public Member(String name, String nickname, String email, String password) {
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
    }

    public Member(MemberJoinRequest joinRequest) {
        this(joinRequest.getName(), joinRequest.getNickname(), joinRequest.getEmail(), joinRequest.getPassword());
    }

    public void createAccount(Account account) {
        this.account = account;
    }
}

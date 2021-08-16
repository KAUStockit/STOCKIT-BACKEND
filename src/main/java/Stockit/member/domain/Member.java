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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
    
    private static final int INITIAL_BALANCE = 1000000; // 첫 회원 잔고

    ////////////////////////////////////////////////////////////////////////////

    public Member(String name, String nickname, String email, String password) {
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.password = convertPassword(password);

        this.role = Role.USER; // 기본은 유저
        this.beforeBalance = 0;
        this.balance = INITIAL_BALANCE;
        this.earningRate = 0d;
    }

    public Member(MemberDto memberDto) {
        this(memberDto.getName(), memberDto.getNickname(), memberDto.getEmail(), "");
        this.password = convertPassword(memberDto.getPassword());
    }

    ////////////////////////////////////////////////////////////////////////////

    private String convertPassword(String password) {
        return sha256(password);
    }

    //비밀번호 암호화 알고리즘
    public static String sha256(String msg) {
        String sha = "";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(msg.getBytes());
            byte byteData[] = md.digest();
            StringBuffer sb = new StringBuffer();
            for (byte byteDatum : byteData) {
                sb.append(Integer.toString((byteDatum & 0xff) + 0x100, 16).substring(1));
            }
            sha = sb.toString();
        } catch(NoSuchAlgorithmException e) {
            //Error
            sha = null;
        }
        return sha;
    }
}

package Stockit.member.domain;

import Stockit.member.dto.MemberDto;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

@Getter
@ToString
@Entity
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_idx")
    private Long idx;

    private String name;

    @Column(unique = true)
    private String nickname;

    @Column(unique = true)
    private String email;

    private String password;

    @Setter
    private int balance = 1000000;

    //이전 주의 최종 balance
    private int beforeBalance = 0;

    @CreatedDate
    private LocalDateTime createdTime;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    private Double earningRate = 0.0;

    @NotNull
    private String role = Role.ROLE_USER.name();

    public Member(String name, String nickname, String email, String password) {
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.password = convertPassword(password);
    }

    public Member(MemberDto memberDto) {
        this(memberDto.getName(), memberDto.getNickname(), memberDto.getEmail(), "");
        this.password = convertPassword(memberDto.getPassword());
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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

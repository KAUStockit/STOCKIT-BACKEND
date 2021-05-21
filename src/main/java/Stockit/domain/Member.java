package Stockit.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;
    private String Nickname;

    @Column(name="email", unique = true)

    private String email;
    private String password;
    private Long balance = 1000000L;


    @OneToMany(mappedBy = "member") //1:N관계이므로 list사용. 1쪽이므로 owner객체 아님
    private List<Order> orders = new ArrayList<>();

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void setPassword(String password) {
        this.password = sha256(password);
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

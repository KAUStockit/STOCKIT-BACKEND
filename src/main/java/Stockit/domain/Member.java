package Stockit.domain;

import lombok.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Getter
public class Member {

    private Long id;
    private final String name;
    private final String nickname;
    private final String email;
    private String password;
    private final Long balance;

    public Member(String name, String nickname, String email, String password) {
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.password = sha256(password);
        this.balance = 1000000L; //초기값 10만원
    }

    public Long setPassword(String password) {
        this.password = password;
        return getId();
    }

    public Long setId(Long id) {
        this.id = id;
        return getId();
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

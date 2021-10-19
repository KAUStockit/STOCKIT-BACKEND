package Stockit.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MemberJoinRequest {

    private String name;
    private String nickname;
    private String email;
    private String password;

    public void encodePassword(String password) {
        this.password = password;
    }
}

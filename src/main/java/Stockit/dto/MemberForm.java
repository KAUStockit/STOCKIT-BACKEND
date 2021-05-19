package Stockit.dto;

import Stockit.domain.Member;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberForm {
    private Long id;
    private String name;
    private String nickname;
    private String email;
    private String password;
    private Long balance;

    public Member toMember() {
        return new Member(name, nickname, email, password);
    }
}

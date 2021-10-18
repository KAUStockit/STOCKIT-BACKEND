package Stockit.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
public class MemberJoinRequest {

    private String name;
    private String nickname;
    private String email;
    @Setter
    private String password;
}

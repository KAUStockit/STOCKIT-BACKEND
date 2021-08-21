package Stockit.member.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class MemberDto {

    private String name;
    private String nickname;
    private String email;
    private String password;
}

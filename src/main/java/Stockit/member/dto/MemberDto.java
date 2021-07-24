package Stockit.member.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.*;

@NoArgsConstructor
@Data
public class MemberDto {

    private String name;
    private String nickname;
    private String email;
    private String password;
}

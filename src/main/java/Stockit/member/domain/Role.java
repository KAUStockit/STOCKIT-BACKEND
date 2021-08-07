package Stockit.member.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum Role {
    ROLE_USER("ROLE_USER", "사용자 권한"),
    ROLE_PRO( "ROLE_PRO", "관리자 권한"),
    ROLE_ADMIN( "ROLE_ADMIN", "최고 관리자 권한");

    private String roleCode;
    private String roleDescription;

    public static Role of(String code) {
        return Arrays.stream(Role.values())
                .filter(r -> r.getRoleCode().equals(code))
                .findAny()
                .orElse(ROLE_USER);
    }
}
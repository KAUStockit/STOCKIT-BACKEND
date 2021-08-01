package Stockit.member.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleType {
    ROLE_USER(0, "ROLE_USER"),
    ROLE_PRO(1, "ROLE_PRO"),
    ROLE_ADMIN(2, "ROLE_ADMIN");

    private Integer codeRole;
    private String strRole;

    public static RoleType getRoleType(Integer codeRole) {
        if (codeRole == null) {
            return null;
        }

        switch(codeRole) {

            case 0:
                return RoleType.ROLE_USER;
            case 1:
                return RoleType.ROLE_PRO;
            case 2:
                return RoleType.ROLE_ADMIN;

            default:
                return null;
        }
    }

    public static RoleType getRoleType(String strRole) {
        if (strRole == null) {
            return null;
        }

        switch(strRole) {

            case "ROLE_USER":
                return RoleType.ROLE_USER;
            case "ROLE_PRO":
                return RoleType.ROLE_PRO;
            case "ROLE_ADMIN":
                return RoleType.ROLE_ADMIN;

            default:
                return null;
        }
    }
}
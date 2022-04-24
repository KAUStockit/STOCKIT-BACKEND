package Stockit.domain.member;

import java.util.Arrays;

public enum Role {
    USER,
    PRO,
    ADMIN;

    public static Role of(String roleName) {
        return Arrays.stream(Role.values())
                .filter(r -> r.name().equals(roleName))
                .findAny()
                .orElse(Role.USER);
    }
}
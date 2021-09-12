package Stockit.jwt;

import Stockit.jwt.provider.JwtAuthTokenProvider;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class CustomJwtConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final JwtAuthTokenProvider jwtAuthTokenProvider;

    public CustomJwtConfigurer(JwtAuthTokenProvider jwtAuthTokenProvider) {
        this.jwtAuthTokenProvider = jwtAuthTokenProvider;
    }

    @Override
    public void configure(HttpSecurity http) {
        CustomJwtFilter customFilter = new CustomJwtFilter(jwtAuthTokenProvider);
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }
}

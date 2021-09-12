package Stockit.jwt;

import Stockit.jwt.provider.JwtAuthTokenProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfiguration {

    @Value("${jwt.base64-secret}")
    private String base64Secret;

    @Bean
    public JwtAuthTokenProvider jwtAuthTokenProvider() { return new JwtAuthTokenProvider(base64Secret);}
}

package Stockit.security;

import Stockit.jwt.CustomJwtConfigurer;
import Stockit.jwt.handler.JwtAccessDeniedHandler;
import Stockit.jwt.handler.JwtAuthenticationEntryPoint;
import Stockit.jwt.provider.JwtAuthTokenProvider;
import Stockit.jwt.service.CustomUserDetailsService;
import Stockit.member.domain.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;
import java.util.List;

/*
참고
https://oingdaddy.tistory.com/206
https://oddpoet.net/blog/2017/04/27/cors-with-spring-security/
https://sowells.tistory.com/170
 */
@RequiredArgsConstructor
@EnableWebSecurity
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomUserDetailsService userDetailsService;
    private final JwtAuthTokenProvider jwtAuthTokenProvider;
    private final JwtAuthenticationEntryPoint authenticationErrorHandler;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Value("${client.max_sec}") private long MAX_AGE_SECS;
    @Value("${client.urls}") private List<String> clientUrls;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors().configurationSource(corsConfigurationSource()).and()

                .exceptionHandling()
                .authenticationEntryPoint(authenticationErrorHandler)
                .accessDeniedHandler(jwtAccessDeniedHandler).and()

                .authorizeRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .antMatchers(
                        "/api/members/login/**",
                        "/api/members/new",
                        "/api/stocks/info/**"
                ).permitAll()
                .antMatchers("api/members/admin").hasAnyAuthority(Role.ADMIN.name())
                .anyRequest().authenticated().and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

                .apply(customJwtConfigurer());
    }

    private CustomJwtConfigurer customJwtConfigurer() {
        return new CustomJwtConfigurer(jwtAuthTokenProvider);
    }

    @Bean(name = "authenticationManager")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(clientUrls);
        configuration.setAllowedMethods(Collections.singletonList("*"));
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.setMaxAge(MAX_AGE_SECS);
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
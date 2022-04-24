package Stockit.config.security.jwt;

import Stockit.domain.member.Member;
import Stockit.infrastructure.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return memberRepository.findByEmail(email)
                .map(this::createSpringUser)
                .orElseThrow(IllegalArgumentException::new);
    }

    private User createSpringUser(Member member) {
        return new User(member.getEmail(), member.getPassword(), new ArrayList<>());
    }
}

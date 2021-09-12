package Stockit.jwt.service;

import Stockit.exception.MemberNotFoundException;
import Stockit.member.domain.Member;
import Stockit.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return memberRepository.findByEmail(email)
                .map(this::createSpringUser)
                .orElseThrow(MemberNotFoundException::new);
    }

    private User createSpringUser(Member member) {
        List<GrantedAuthority> grantedAuthorityList = Collections.singletonList(new SimpleGrantedAuthority(member.getRole().name()));
        return new User(member.getEmail(), member.getPassword(), grantedAuthorityList);
    }
}

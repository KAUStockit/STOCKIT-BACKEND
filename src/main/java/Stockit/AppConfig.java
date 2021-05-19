package Stockit;

import Stockit.repository.MemberRepository;
import Stockit.repository.MemoryMemberRepository;
import Stockit.service.MemberService;
import Stockit.service.MemberServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }
}

package Stockit.domain.member;

import Stockit.config.security.jwt.JwtUtil;
import Stockit.infrastructure.member.MemberRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class MemberServiceImplTest {

    @Mock
    private MemberReader memberReader;

    @Mock
    private MemberRepository memberRepository;

    @Spy
    private AuthenticationManager authenticationManager;

    @Spy
    private JwtUtil jwtUtil;

    @InjectMocks
    private MemberService memberService;

}
package Stockit.domain.member;

import Stockit.config.security.jwt.JwtUtil;
import Stockit.domain.member.dto.MemberInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MemberServiceImplTest {

    @Mock
    private MemberReader memberReader;

    @Mock
    private MemberStore memberStore;

    @Spy
    private AuthenticationManager authenticationManager;

    @Spy
    private JwtUtil jwtUtil;

    @InjectMocks
    private MemberServiceImpl memberService;

    @Test
    void 존재하는_멤버_아이디로_멤버를_조회할_수_있다() {

    }

    @Test
    void 존재하지_않는_멤버_아이디로_멤버를_조회할_수_있다() {

    }
}
package Stockit.webchat;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;


//핸들러로 웹소켓을 활성화 하기위해 Config작성. -> EnableWebSocket이라는 Annotation이 가능하게 해준다.
@Configuration
@RequiredArgsConstructor
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    private final ChatHandler chatHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        //Endpoint는 /chat으로 설정한다.
        /* ws://localhost:8080/chat으로 커넥션 연결 후 메시지 통신 준비 완료 */
        registry.addHandler(chatHandler, "/chat").setAllowedOrigins("*");
    }
}

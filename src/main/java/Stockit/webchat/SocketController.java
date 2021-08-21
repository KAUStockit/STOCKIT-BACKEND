package Stockit.webchat;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class SocketController {

    // /receive를 메시지를 받을 endpoint로 설정
    @MessageMapping("/receive")

    // /send로 메시지를 반환합니다.
    @SendTo("/send")

    // SocketHandler는 1) /receive에서 메시지를 받고, /send로 메시지를 보내준다.
    // 정의한 SocketVO를 1) 인자값, 2) 반환값으로 사용
    public SocketVO SocketHandler(SocketVO socketVO) {

        String content = socketVO.getContent();

        SocketVO result = new SocketVO(content);
        return result;
    }
}
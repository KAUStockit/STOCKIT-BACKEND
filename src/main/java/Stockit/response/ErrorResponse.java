package Stockit.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ErrorResponse implements BasicResponse {
    int statusCode;
    String message;
}

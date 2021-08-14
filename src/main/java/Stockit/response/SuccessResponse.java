package Stockit.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SuccessResponse<T> extends BasicResponse {
    private T data;
    private String message;
}

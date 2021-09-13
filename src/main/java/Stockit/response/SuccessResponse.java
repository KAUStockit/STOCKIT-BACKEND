package Stockit.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SuccessResponse<T> implements BasicResponse {
    private final int statusCode;
    private final String message;
    private final T data;
}

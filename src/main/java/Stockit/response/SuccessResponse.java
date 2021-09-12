package Stockit.response;

import lombok.Getter;

@Getter
public class SuccessResponse<T> extends BasicResponse {
    private T data;

    public SuccessResponse(int statusCode, String message, T data) {
        super(statusCode, message);
        this.data = data;
    }
}

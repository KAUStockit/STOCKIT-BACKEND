package Stockit.response;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class BasicResponse {
    private int statusCode;
    private String message;
}

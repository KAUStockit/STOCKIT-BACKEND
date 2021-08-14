package Stockit.response;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ErrorResponse extends BasicResponse {
    private final String errorMessage;
}

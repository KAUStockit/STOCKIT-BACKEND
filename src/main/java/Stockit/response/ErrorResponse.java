package Stockit.response;

public class ErrorResponse extends BasicResponse {
    public ErrorResponse(int statusCode, String message) {
        super(statusCode, message);
    }
}

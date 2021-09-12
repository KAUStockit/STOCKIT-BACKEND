package Stockit.exception;

public class LoginFailedException extends RuntimeException {

    public LoginFailedException() {
        super(ErrorCode.AUTHENTICATION_FAILED.getMessage());
    }
}

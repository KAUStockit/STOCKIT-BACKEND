package Stockit.exception;

public class TokenValidFailedException extends RuntimeException {

    public TokenValidFailedException() {
        super(ErrorCode.TOKEN_GENERATION_FAILED.getMessage());
    }
}

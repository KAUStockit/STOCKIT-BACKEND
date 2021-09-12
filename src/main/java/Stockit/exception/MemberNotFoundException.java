package Stockit.exception;

public class MemberNotFoundException extends RuntimeException {

    public MemberNotFoundException() {
        super(ErrorCode.AUTHENTICATION_FAILED.getMessage());
    }
}

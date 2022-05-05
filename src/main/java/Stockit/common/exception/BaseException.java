package Stockit.common.exception;

import Stockit.common.response.ErrorCode;
import lombok.Getter;

/**
 * 개발자가 인지한 예외를 표현
 *
 * http status: 200 이면서 resultCode: FAIL 을 표현한다
 */
@Getter
public class BaseException extends RuntimeException {
    private ErrorCode errorCode;

    public BaseException() {
    }

    public BaseException(ErrorCode errorCode) {
        super(errorCode.getErrorMsg());
        this.errorCode = errorCode;
    }

    public BaseException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public BaseException(String message, ErrorCode errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }
}

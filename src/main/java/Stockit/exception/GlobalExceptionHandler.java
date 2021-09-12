package Stockit.exception;

import Stockit.response.BasicResponse;
import Stockit.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<BasicResponse> handleRuntimeException(RuntimeException e) {

        log.info("handleRuntimeException", e);

        BasicResponse response = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "런타임에러");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(LoginFailedException.class)
    protected ResponseEntity<BasicResponse> handleLoginFailedException(LoginFailedException e) {

        log.info("handleLoginFailedException", e);

        BasicResponse response = new ErrorResponse(ErrorCode.Login_FAILED.getStatus(), e.getMessage());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body(response);
    }

    @ExceptionHandler(BadCredentialsException.class)
    protected ResponseEntity<BasicResponse> handleBadCredentialsException(BadCredentialsException e) {

        log.info("handleBadCredentialsException", e);

        ErrorResponse response = new ErrorResponse(ErrorCode.Login_FAILED.getStatus(), e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<BasicResponse> handleAccessDeniedException(AccessDeniedException e) {

        log.info("handleAccessDeniedException", e);

        ErrorResponse response = new ErrorResponse(ErrorCode.ACCESS_DENIED.getStatus(), e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body(response);
    }

    @ExceptionHandler(InsufficientAuthenticationException.class)
    protected ResponseEntity<BasicResponse> handleInsufficientAuthenticationException(InsufficientAuthenticationException e) {

        log.info("handleInsufficientAuthenticationException", e);

        ErrorResponse response = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), e.getMessage());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED.value()).body(response);
    }
}
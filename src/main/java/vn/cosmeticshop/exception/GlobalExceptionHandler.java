package vn.cosmeticshop.exception;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.SignatureException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ProblemDetail build(int status, String message, String description) {
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(status), message);
        detail.setProperty("description", description);
        return detail;
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ProblemDetail handleBadCredentials(BadCredentialsException exception) {
        return build(401, exception.getMessage(), "The email or password is incorrect");
    }

    @ExceptionHandler(AccountStatusException.class)
    public ProblemDetail handleAccountStatus(AccountStatusException exception) {
        return build(403, exception.getMessage(), "The account is locked");
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ProblemDetail handleAccessDenied(AccessDeniedException exception) {
        return build(403, exception.getMessage(), "You are not authorized to access this resource");
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ProblemDetail handleExpiredJwt(ExpiredJwtException exception) {
        return build(401, exception.getMessage(), "The JWT token has expired");
    }

    @ExceptionHandler(SignatureException.class)
    public ProblemDetail handleSignature(SignatureException exception) {
        return build(401, exception.getMessage(), "The JWT signature is invalid");
    }

    @ExceptionHandler(JwtException.class)
    public ProblemDetail handleJwt(JwtException exception) {
        return build(401, exception.getMessage(), "The JWT token is invalid");
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleOther(Exception exception) {
        exception.printStackTrace();
        return build(500, exception.getMessage(), "Unknown internal server error.");
    }
}
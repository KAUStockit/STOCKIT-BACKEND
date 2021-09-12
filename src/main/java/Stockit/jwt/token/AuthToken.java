package Stockit.jwt.token;

public interface AuthToken<T> {
    boolean validate();
    T getData();
}

package twitter.clone.auth_service.exception;

import javax.naming.AuthenticationException;

public class JWTTokenMissingException extends AuthenticationException {
    private static final long serialVersionUID = 1L;
    public JWTTokenMissingException(String msg) {
        super(msg);
    }
}

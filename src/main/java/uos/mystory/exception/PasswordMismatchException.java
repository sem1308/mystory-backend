package uos.mystory.exception;

import uos.mystory.exception.massage.MessageManager;

public class PasswordMismatchException extends RuntimeException {
    private static final String defaultMessage = MessageManager.getMessage("error.mismatch.user.user_pw");
    public PasswordMismatchException(String message) {
        super(message);
    }

    public PasswordMismatchException() {
        super(defaultMessage);
    }

    public PasswordMismatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public PasswordMismatchException(Throwable cause) {
        super(cause);
    }
}

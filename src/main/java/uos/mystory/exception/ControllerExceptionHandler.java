package uos.mystory.exception;
import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalExceptionHandler(Exception ex, WebRequest request) {

        String url = ((ServletWebRequest) request).getRequest().getRequestURI();

        ErrorResponse message = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), new Date(), url,
                ex.getMessage(), request.getDescription(false));

        return new ResponseEntity<ErrorResponse>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex,
                                                                         WebRequest request) {
        String url = ((ServletWebRequest) request).getRequest().getRequestURI();

        ErrorResponse message = new ErrorResponse(HttpStatus.NOT_FOUND.value(), new Date(), url, ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<ErrorResponse>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateException(DuplicateException ex, WebRequest request) {
        String url = ((ServletWebRequest) request).getRequest().getRequestURI();

        ErrorResponse message = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), new Date(), url, ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<ErrorResponse>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorResponse> handleForbiddenException(ForbiddenException ex, WebRequest request) {
        String url = ((ServletWebRequest) request).getRequest().getRequestURI();

        ErrorResponse message = new ErrorResponse(HttpStatus.FORBIDDEN.value(), new Date(), url, ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<ErrorResponse>(message, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({ MethodArgumentNotValidException.class })
    public ResponseEntity<ErrorResponse> validException(MethodArgumentNotValidException ex, WebRequest request) {
        String url = ((ServletWebRequest) request).getRequest().getRequestURI();

        String firstBindingMessage = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        ErrorResponse message = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), new Date(), url, firstBindingMessage,
                request.getDescription(false));

        return new ResponseEntity<ErrorResponse>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LimitExceededException.class)
    public ResponseEntity<ErrorResponse> handleLimitExceededException(LimitExceededException ex, WebRequest request) {
        String url = ((ServletWebRequest) request).getRequest().getRequestURI();

        ErrorResponse message = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), new Date(), url, ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<ErrorResponse>(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MismatchException.class)
    public ResponseEntity<ErrorResponse> handleMismatchException(MismatchException ex, WebRequest request) {
        String url = ((ServletWebRequest) request).getRequest().getRequestURI();

        ErrorResponse message = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), new Date(), url, ex.getMessage(),
                request.getDescription(false));

        return new ResponseEntity<ErrorResponse>(message, HttpStatus.BAD_REQUEST);
    }
}

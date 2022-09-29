package gr.unipi.solarparkmanager.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
        ApiError apiError = new ApiError();
        apiError.setMessage(ex.getMessage());
        return ResponseEntity.internalServerError().body(apiError);
    }

    @ExceptionHandler({SolarFacilityNotFoundException.class, SolarPanelNotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(Exception ex, HttpServletRequest request) {
        ApiError apiError = new ApiError();
        apiError.setPath(request.getRequestURI());
        apiError.setMethod(request.getMethod());
        apiError.setStatus(HttpStatus.NOT_FOUND);
        apiError.setMessage(ex.getMessage());
        String sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
        apiError.setTime(sdf);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiError apiError = new ApiError();
        apiError.setPath(((ServletWebRequest) request).getRequest().getRequestURI());
        apiError.setStatus(HttpStatus.BAD_REQUEST);
        apiError.setMessage(ex.getMessage());
        apiError.setMethod(((ServletWebRequest) request).getHttpMethod().toString());
        String sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
        apiError.setTime(sdf);
        ex.getBindingResult().getAllErrors().forEach((error) -> apiError.getErrors().add(error.getDefaultMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }
}

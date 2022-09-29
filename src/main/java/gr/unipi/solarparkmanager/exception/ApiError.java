package gr.unipi.solarparkmanager.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {

    public ApiError() {
        this.errors = new ArrayList<>();
    }

    private String message;

    private HttpStatus status;

    private List<String> errors;

    private String time;

    private String path;

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    private String method;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public List<String> getErrors() {
        return errors == null ? new ArrayList<>() : errors;
    }

    public void setError(List<String> errors) {
        this.errors = errors;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

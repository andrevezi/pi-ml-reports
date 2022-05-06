package pimlreports.report.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import pimlreports.report.exception.EmptyCartException;


@ControllerAdvice(annotations = RestController.class)
public class MyExceptionHandler {

    @ExceptionHandler(EmptyCartException.class)
    protected ResponseEntity<?> emptyCart() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Empty Cart");
    }
}


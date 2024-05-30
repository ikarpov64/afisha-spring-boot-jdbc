package org.javaacademy.afisha.exception.handler;

import java.time.Instant;
import org.javaacademy.afisha.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({PlaceNotFoundException.class, EventNotFoundException.class,
        EventTypeNotFoundException.class, TicketNotFoundException.class})
    public ProblemDetail handleEntityNotFoundException(RuntimeException e) {
        ProblemDetail problemDetail = createProblemDetail(HttpStatus.NOT_FOUND, e);
        problemDetail.setTitle("Object not found");
        problemDetail.setProperty("errorCode", "NOT_FOUND");
        return problemDetail;
    }

    @ExceptionHandler(TicketNotAvailableException.class)
    public ProblemDetail handleTicketNotAvailableException(TicketNotAvailableException e) {
        ProblemDetail problemDetail = createProblemDetail(HttpStatus.NOT_ACCEPTABLE, e);
        problemDetail.setTitle("Object not found");
        problemDetail.setProperty("errorCode", "NOT_ACCEPTABLE");
        return problemDetail;
    }

    @ExceptionHandler(EventCannotBeSavedException.class)
    public ProblemDetail handleEventCannotBeSaveException(EventCannotBeSavedException e) {
        ProblemDetail problemDetail = createProblemDetail(HttpStatus.NOT_FOUND, e);
        problemDetail.setTitle("Place or EventType not found");
        problemDetail.setProperty("errorCode", "NOT_FOUND");
        return problemDetail;
    }

    private ProblemDetail createProblemDetail(HttpStatus status, Exception e) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, e.getMessage());
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }
}

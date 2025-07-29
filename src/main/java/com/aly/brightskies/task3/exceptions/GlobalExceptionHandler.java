package com.aly.brightskies.task3.exceptions;

import com.aly.brightskies.task3.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.time.Instant;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserException.class)
    public ResponseEntity<ErrorDTO> handleUserException(UserException ex, WebRequest request) {
        ErrorDTO errorDTO = new ErrorDTO(
                Instant.now(),
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                request.getDescription(false));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorDTO> handleUnauthorizedException(UnauthorizedException ex, WebRequest request) {
        ErrorDTO errorDTO = new ErrorDTO(
                Instant.now(),
                HttpStatus.UNAUTHORIZED.value(),
                ex.getMessage(),
                HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                request.getDescription(false));
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorDTO);
    }


    @ExceptionHandler(RoomException.class)
    public ResponseEntity<ErrorDTO> handleRoomException(RoomException ex, WebRequest request) {
        ErrorDTO errorDTO = new ErrorDTO(
                Instant.now(),
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                request.getDescription(false));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }

    @ExceptionHandler(ReservationException.class)
    public ResponseEntity<ErrorDTO> handleReservationException(ReservationException ex, WebRequest request) {
        ErrorDTO errorDTO = new ErrorDTO(
                Instant.now(),
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                request.getDescription(false));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }

}



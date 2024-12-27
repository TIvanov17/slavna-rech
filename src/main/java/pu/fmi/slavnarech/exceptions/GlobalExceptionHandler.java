package pu.fmi.slavnarech.exceptions;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pu.fmi.slavnarech.exceptions.errors.ErrorResponse;
import pu.fmi.slavnarech.exceptions.errors.InvalidInputErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  private static final String INVALID_REQUEST_FIELD_ERROR_MESSAGE = "Failed request validation";

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatusCode status,
      WebRequest request) {

    Map<String, String> validationErrors = new HashMap<>();
    List<ObjectError> validationErrorList = ex.getBindingResult().getAllErrors();

    InvalidInputErrorResponse errorResponse = new InvalidInputErrorResponse();
    errorResponse.setStatusCode(HttpStatus.BAD_REQUEST.value());
    errorResponse.setStatusMessage(HttpStatus.BAD_REQUEST);
    errorResponse.setDate(LocalDateTime.now());
    errorResponse.setMessage(INVALID_REQUEST_FIELD_ERROR_MESSAGE);

    validationErrorList.forEach(
        error -> {
          String fieldName = ((FieldError) error).getField();
          String validationMessage = error.getDefaultMessage();
          validationErrors.put(fieldName, validationMessage);
        });

    errorResponse.setInvalidFields(validationErrors);
    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException e) {
    return new ResponseEntity<>(
        new ErrorResponse(HttpStatus.NOT_FOUND, e.getLocalizedMessage(), LocalDateTime.now()),
        HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(ApplicationException.class)
  public ResponseEntity<ErrorResponse> handleGenericException(ApplicationException e) {
    return new ResponseEntity<>(
        new ErrorResponse(HttpStatus.BAD_REQUEST, e.getLocalizedMessage(), LocalDateTime.now()),
        HttpStatus.BAD_REQUEST);
  }
}

package uk.jakebaum.recipe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.*;

public class ControllerAdvice {

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public Map<String, List<String>> handleValidationException(MethodArgumentNotValidException e) {
    Map<String, List<String>> validationErrors = new HashMap<>();

    e.getBindingResult().getAllErrors().forEach(error -> {
      String fieldName = ((FieldError)error).getField();
      String errorMessage = error.getDefaultMessage();

      validationErrors.computeIfAbsent(fieldName, key -> new ArrayList<>()).add(errorMessage);
    });

    return validationErrors;
  }

}

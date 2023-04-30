package uk.jakebaum.recipe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class EntityDoesNotExistException extends ResponseStatusException {

  public <T> EntityDoesNotExistException(Class<?> clazz, T id) {
    super(HttpStatus.NOT_FOUND, String.format("%s with ID %s does not exist", clazz, id));
  }

}

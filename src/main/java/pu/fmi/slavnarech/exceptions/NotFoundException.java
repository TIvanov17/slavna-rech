package pu.fmi.slavnarech.exceptions;

public class NotFoundException extends ApplicationException {

  public NotFoundException(Class<?> clazz, String property, Object value) {
    super(
        String.format("Could not found %s with %s = %s.", clazz.getSimpleName(), property, value));
  }

  public NotFoundException(Class<?> clazz, Long id) {
    this(clazz, "id", id);
  }
}

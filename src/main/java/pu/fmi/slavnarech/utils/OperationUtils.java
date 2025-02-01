package pu.fmi.slavnarech.utils;

import java.util.function.Consumer;

public class OperationUtils {

  private OperationUtils() {}

  public static <T> void updateIfChanged(T oldValue, T newValue, Consumer<T> setter) {
    if (newValue != null && !newValue.equals(oldValue)) {
      setter.accept(newValue);
    }
  }
}

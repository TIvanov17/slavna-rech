package pu.fmi.slavnarech.exceptions.errors;

import java.util.Map;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class InvalidInputErrorResponse extends ErrorResponse {

  private Map<String, String> invalidFields;
}

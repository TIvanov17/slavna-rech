package pu.fmi.slavnarech.exceptions.errors;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
public class ErrorResponse {

  private Integer statusCode;

  private HttpStatus statusMessage;

  private String message;

  private LocalDateTime date;

  public ErrorResponse(HttpStatus status, String message, LocalDateTime date) {
    this.statusCode = status.value();
    this.statusMessage = status;
    this.message = message;
    this.date = date;
  }
}

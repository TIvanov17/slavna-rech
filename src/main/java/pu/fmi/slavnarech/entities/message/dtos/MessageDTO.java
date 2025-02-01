package pu.fmi.slavnarech.entities.message.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pu.fmi.slavnarech.entities.user.dtos.UserResponse;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {

  private Long connectionId;
  private UserResponse sender;
  private String content;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createdOn;
}

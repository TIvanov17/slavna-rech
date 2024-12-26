package pu.fmi.slavnarech.entities;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class Message {

  private Long id;

  private Member sender;

  private String content;

  private LocalDateTime createdOn;

  private boolean isActive;
}

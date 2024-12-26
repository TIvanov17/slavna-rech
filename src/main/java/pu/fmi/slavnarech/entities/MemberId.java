package pu.fmi.slavnarech.entities;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@Embeddable
public class MemberId implements Serializable {

  private Long userId;

  private Long connectionId;

}

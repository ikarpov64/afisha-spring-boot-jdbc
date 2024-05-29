package org.javaacademy.afisha.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportDtoRs {
    private String eventName;
    private String eventType;
    private Long ticketSold;
    private BigDecimal soldAmount;
}

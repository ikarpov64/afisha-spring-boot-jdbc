package org.javaacademy.afisha.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportDtoRs {
    private String eventName;
    private String eventType;
    private Long ticketSold;
    private BigDecimal soldAmount;
}

package org.javaacademy.afisha.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketDto {
    private Long id;
    private EventDto event;
    private String clientEmail;
    private BigDecimal price;
    private boolean isSold;
}

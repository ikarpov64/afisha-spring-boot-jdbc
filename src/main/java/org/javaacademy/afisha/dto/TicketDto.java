package org.javaacademy.afisha.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

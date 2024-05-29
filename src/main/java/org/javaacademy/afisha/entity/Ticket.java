package org.javaacademy.afisha.entity;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class Ticket {
    private Long id;
    private Event event;
    private String clientEmail;
    private BigDecimal price;
    private boolean isSold;
}

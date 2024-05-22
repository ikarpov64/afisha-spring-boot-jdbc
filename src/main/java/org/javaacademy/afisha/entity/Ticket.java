package org.javaacademy.afisha.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Ticket {
    private Long id;
    private Event event;
    private String clientEmail;
    private BigDecimal price;
    private boolean isSold;
}

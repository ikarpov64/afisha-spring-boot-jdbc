package org.javaacademy.afisha.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventDtoRq {
    private Long id;
    private String name;
    private LocalDateTime eventDate;
    private PlaceDto place;
    private BigDecimal price;
}

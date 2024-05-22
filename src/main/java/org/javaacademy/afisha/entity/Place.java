package org.javaacademy.afisha.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Place {
    private Long id;
    private String name;
    private String address;
    private String city;
    private List<Event> events = new ArrayList<>();
}

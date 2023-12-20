package com.univrouen.ollcalamaison.dto.output;

import lombok.Data;

import java.time.Instant;

@Data
public class DeliveryPersonDto {

    private Long id;

    private String name;

    private boolean isAvailable;

    private Instant creation;

    private int numberOfDeliveries;

    private int numberOfTours;

}

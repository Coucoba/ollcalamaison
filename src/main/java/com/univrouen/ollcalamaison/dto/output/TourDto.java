package com.univrouen.ollcalamaison.dto.output;

import com.univrouen.ollcalamaison.dto.input.InputDeliveryDto;
import lombok.Data;

import java.time.Instant;
import java.util.Set;


@Data
public class TourDto {

    private Long id;

    private String name;

    private Instant startDate;

    private Instant endDate;

    private DeliveryPersonDto deliveryPerson;

    private Set<DeliveryDto> deliveries;

    private int numberOfDeliveries;
}

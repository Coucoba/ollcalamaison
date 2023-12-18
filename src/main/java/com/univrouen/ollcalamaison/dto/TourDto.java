package com.univrouen.ollcalamaison.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Builder
@Data
public class TourDto {

    private String name;

    private Instant start;

    private Instant end;

    private Long deliveryPersonId;

    private List<DeliveryDto> deliveries;

    private int numberOfDeliveries;
}

package com.univrouen.ollcalamaison.dto.output;

import com.univrouen.ollcalamaison.dto.input.InputDeliveryDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Set;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TourDto {

    private String name;

    private Instant startDate;

    private Instant endDate;

    private Long deliveryPersonId;

    private Set<InputDeliveryDto> deliveries;

    private int numberOfDeliveries;
}

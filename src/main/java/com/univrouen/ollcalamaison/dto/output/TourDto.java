package com.univrouen.ollcalamaison.dto.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TourDto {

    private Long id;

    private String name;

    private Instant startDate;

    private Instant endDate;

    private DeliveryPersonDto deliveryPerson;

    private List<DeliverySimplifiedDto> deliveries;
}

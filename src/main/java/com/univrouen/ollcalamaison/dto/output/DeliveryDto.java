package com.univrouen.ollcalamaison.dto.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryDto {

    private Long id;

    private String pickupAddress;

    private String depositAddress;

    private TourSimplifiedDto tour;

}

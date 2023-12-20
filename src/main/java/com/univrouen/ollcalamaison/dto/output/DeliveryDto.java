package com.univrouen.ollcalamaison.dto.output;

import lombok.Data;

@Data
public class DeliveryDto {

    private Long id;

    private String pickupAddress;

    private String depositAddress;

    private TourDto tour;

}

package com.univrouen.ollcalamaison.dto.output;

import lombok.Data;

@Data
public class DeliverySimplifiedDto {

    private Long id;

    private String pickupAddress;

    private String depositAddress;

}

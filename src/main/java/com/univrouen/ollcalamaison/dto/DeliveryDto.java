package com.univrouen.ollcalamaison.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DeliveryDto {

    private String pickupAddress;

    private String depositAddress;
}

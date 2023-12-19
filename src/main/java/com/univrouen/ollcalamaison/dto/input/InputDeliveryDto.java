package com.univrouen.ollcalamaison.dto.input;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class InputDeliveryDto {

    private String pickupAddress;

    private String depositAddress;
}

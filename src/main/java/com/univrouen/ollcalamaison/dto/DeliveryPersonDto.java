package com.univrouen.ollcalamaison.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DeliveryPersonDto {

    private String firstName;

    private String lastName;

    private boolean isAvailable;

}

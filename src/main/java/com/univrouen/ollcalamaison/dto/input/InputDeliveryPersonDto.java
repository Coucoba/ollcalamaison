package com.univrouen.ollcalamaison.dto.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InputDeliveryPersonDto {

    private String name;

    private boolean isAvailable;
}

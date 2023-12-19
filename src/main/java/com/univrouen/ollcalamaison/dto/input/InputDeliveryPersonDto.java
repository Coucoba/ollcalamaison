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

    private String first_Name;

    private String last_Name;

    private boolean isAvailable;
}

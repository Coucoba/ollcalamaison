package com.univrouen.ollcalamaison.dto.output;

import com.univrouen.ollcalamaison.dto.input.InputDeliveryDto;
import com.univrouen.ollcalamaison.dto.input.InputTourDto;
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
public class DeliveryPersonDto {

    private Long id;

    private String first_Name;

    private String last_Name;

    private boolean isAvailable;

    private Instant creation;

    private Set<InputDeliveryDto> deliveries;

    private int numberOfDeliveries;

    private Set<InputTourDto> tours;

    private int numberOfTours;

}

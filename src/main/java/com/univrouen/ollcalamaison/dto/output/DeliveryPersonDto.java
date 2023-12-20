package com.univrouen.ollcalamaison.dto.output;

import com.univrouen.ollcalamaison.dto.input.InputDeliveryDto;
import com.univrouen.ollcalamaison.dto.input.InputTourDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
public class DeliveryPersonDto {

    private Long id;

    private String name;

    private boolean isAvailable;

    private Instant creation;

    private int numberOfDeliveries;

    private int numberOfTours;

}

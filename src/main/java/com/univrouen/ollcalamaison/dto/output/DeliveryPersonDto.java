package com.univrouen.ollcalamaison.dto.output;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

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
}

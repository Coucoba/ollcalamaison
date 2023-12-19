package com.univrouen.ollcalamaison.dto.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InputTourDto {

    private String name;

    private Instant startDate;

    private Instant endDate;
}

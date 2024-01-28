package com.univrouen.ollcalamaison.dto.output;

import lombok.Data;

import java.time.Instant;

@Data
public class TourSimplifiedDto {
    private Long id;

    private String name;

    private Instant startDate;

    private Instant endDate;
}

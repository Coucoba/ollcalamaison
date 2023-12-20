package com.univrouen.ollcalamaison.dto.input;


import lombok.Data;


import java.time.Instant;


@Data
public class InputTourDto {

    private String name;

    private Instant startDate;

    private Instant endDate;
}

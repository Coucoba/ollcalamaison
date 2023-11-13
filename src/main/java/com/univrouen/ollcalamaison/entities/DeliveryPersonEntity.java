package com.univrouen.ollcalamaison.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class DeliveryPersonEntity {
    @Id
    private Long id;

    private String first_name;

    private String last_name;

    private boolean isAvailable;
}

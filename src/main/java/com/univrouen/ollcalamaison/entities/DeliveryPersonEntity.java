package com.univrouen.ollcalamaison.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Entity
@Data
public class DeliveryPersonEntity {
    @Id
    private Long id;

    private String first_name;

    private String last_name;

    private boolean isAvailable;

    private Instant creation;

    @OneToMany(mappedBy = "deliveryPerson", cascade = CascadeType.ALL)
    private List<TourEntity> tours;
}

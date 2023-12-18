package com.univrouen.ollcalamaison.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Entity
@Data
public class TourEntity {

    @Id
    private Long id;

    private String name;

    private Instant start;

    private Instant end;

    @ManyToOne
    @JoinColumn(name = "delivery_person_id")
    private DeliveryPersonEntity deliveryPerson;

    @OneToMany(mappedBy = "tour", cascade =  CascadeType.ALL)
    private List<DeliveryEntity> deliveries;
}

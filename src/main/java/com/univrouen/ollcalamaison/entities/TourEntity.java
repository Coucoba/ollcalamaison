package com.univrouen.ollcalamaison.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Entity
@Data
@Table(name = "Tour")
public class TourEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Instant startDate;

    private Instant endDate;

    @ManyToOne
    private DeliveryPersonEntity deliveryPerson;

    @OneToMany(mappedBy = "tour")
    private List<DeliveryEntity> deliveries;
}

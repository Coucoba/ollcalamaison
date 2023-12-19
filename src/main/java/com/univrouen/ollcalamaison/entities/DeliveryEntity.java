package com.univrouen.ollcalamaison.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class DeliveryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String pickupAddress;

    private String depositAddress;

    @ManyToOne
    private TourEntity tour;
}

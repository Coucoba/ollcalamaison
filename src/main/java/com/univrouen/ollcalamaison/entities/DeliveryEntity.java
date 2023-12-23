package com.univrouen.ollcalamaison.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Delivery")
public class DeliveryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String pickupAddress;

    private String depositAddress;

    @ManyToOne
    private DeliveryPersonEntity deliveryPerson;

    @ManyToOne
    private TourEntity tour;
}

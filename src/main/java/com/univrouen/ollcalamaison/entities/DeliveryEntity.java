package com.univrouen.ollcalamaison.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class DeliveryEntity {

    @Id
    private Long id;

    private String pickupAddress;

    private String depositAddress;

    @ManyToOne
    @JoinColumn(name = "tour_id")
    private TourEntity tour;
}

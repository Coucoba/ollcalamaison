package com.univrouen.ollcalamaison.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "DeliveryPerson")
public class DeliveryPersonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private boolean isAvailable;

    private Instant creation;

    @OneToMany(mappedBy = "deliveryPerson")
    private Set<TourEntity> tours;

    @OneToMany(mappedBy = "deliveryPerson")
    private Set<DeliveryEntity> deliveries;
}

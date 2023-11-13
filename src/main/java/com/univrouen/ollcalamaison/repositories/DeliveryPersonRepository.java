package com.univrouen.ollcalamaison.repositories;

import com.univrouen.ollcalamaison.entities.DeliveryPersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryPersonRepository extends JpaRepository<DeliveryPersonEntity, Long> {
}

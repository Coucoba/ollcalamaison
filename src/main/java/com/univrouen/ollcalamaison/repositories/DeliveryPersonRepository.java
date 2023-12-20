package com.univrouen.ollcalamaison.repositories;

import com.univrouen.ollcalamaison.entities.DeliveryPersonEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeliveryPersonRepository extends JpaRepository<DeliveryPersonEntity, Long> {

    Page<DeliveryPersonEntity> findAllByOrderByNameAsc(Pageable pageable);
    Page<DeliveryPersonEntity> findAllByOrderByCreationAsc(Pageable pageable);
    Optional<DeliveryPersonEntity> findByName(String name);

}

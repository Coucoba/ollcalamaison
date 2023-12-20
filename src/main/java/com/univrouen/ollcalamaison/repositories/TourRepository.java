package com.univrouen.ollcalamaison.repositories;

import com.univrouen.ollcalamaison.entities.DeliveryPersonEntity;
import com.univrouen.ollcalamaison.entities.TourEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Set;

@Repository
public interface TourRepository extends JpaRepository<TourEntity, Long> {

    Page<TourEntity> findAllByDeliveryPerson(DeliveryPersonEntity deliveryPerson, Pageable pageable);

    @Query("SELECT t FROM TourEntity t " +
            "WHERE (t.startDate <= :searchDate AND t.endDate >= :searchDate)")
    Page<TourEntity> findByDate(@Param("searchDate") Instant searchDate, Pageable pageable);

}

package com.univrouen.ollcalamaison.repositories;

import com.univrouen.ollcalamaison.entities.TourEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface TourRepository extends JpaRepository<TourEntity, Long> {

    Page<TourEntity> findByDeliveryPersonId(@Param("deliveryPersonId") Long deliveryPersonId, Pageable pageable);

    @Query("SELECT t FROM TourEntity t " +
            "WHERE t.deliveryPerson.id = :deliveryPersonId " +
            "AND ((t.startDate BETWEEN :tourStart AND :tourEnd) OR (t.endDate BETWEEN :tourStart AND :tourEnd))")
    List<TourEntity> findOverlappingTours(
            @Param("deliveryPersonId") Long deliveryPersonId,
            @Param("tourStart") Instant tourStart,
            @Param("tourEnd") Instant tourEnd
    );

    @Query("SELECT t FROM TourEntity t " +
            "WHERE (t.startDate <= :searchDate AND t.endDate >= :searchDate)")
    List<TourEntity> findByDate(@Param("searchDate") Instant searchDate);
}

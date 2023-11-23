package com.example.accommodationbookingservice.repository;

import com.example.accommodationbookingservice.model.Accommodation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {
    @Query("SELECT a FROM Accommodation a "
            + "LEFT JOIN FETCH Booking b ON a.id = b.accommodation.id "
            + "WHERE b.id = :bookingId AND a.isDeleted = false")
    Accommodation findAccommodationByBookingId(@Param("bookingId") Long bookingId);
}

package com.example.accommodationbookingservice.repository;

import static com.example.accommodationbookingservice.model.Booking.Status;

import com.example.accommodationbookingservice.model.Booking;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByUserIdAndStatus(Long userId, Status status);

//    @EntityGraph(attributePaths = {"user.email"})
    List<Booking> findByUserId(Long userId);
}

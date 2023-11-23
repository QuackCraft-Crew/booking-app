package com.example.accommodationbookingservice.repository;

import static com.example.accommodationbookingservice.model.Booking.Status;

import com.example.accommodationbookingservice.model.Booking;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByUserIdAndStatus(Long userId, Status status);

    List<Booking> findByUserId(Long userId);

    List<Booking> findByStatus(Status status);

}

package com.example.accommodationbookingservice.repository;

import com.example.accommodationbookingservice.model.Booking;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import static com.example.accommodationbookingservice.model.Booking.Status;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByUserIdAndStatus(Long userId, Status status);

    List<Booking> findByUserId(Long userId);

    @Query("""
            SELECT b FROM Booking b
            WHERE b.checkOut BETWEEN CURRENT_DATE AND (CURRENT_DATE + 1)
            AND b.isDeleted = false
            """)
    List<Booking> getExpiredBookings();

    List<Booking> findByStatus(Status status);
}

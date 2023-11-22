package com.example.accommodationbookingservice.repository;

import com.example.accommodationbookingservice.model.Payment;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    @EntityGraph(attributePaths = "booking")
    Optional<Payment> findBySessionId(String sessionId);

    @Query(value = "from Payment p "
            + "left join fetch p.booking b "
            + "left join fetch b.user u "
            + "where u.id = :userId")
    List<Payment> getPaymentsByUserId(Long userId);
}

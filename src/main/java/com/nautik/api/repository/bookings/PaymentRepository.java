package com.nautik.api.repository.bookings;

import com.nautik.api.domain.booking.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
}

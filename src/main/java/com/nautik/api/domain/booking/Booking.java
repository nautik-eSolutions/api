package com.nautik.api.domain.booking;

import com.nautik.api.domain.moorings.Mooring;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "total_cost", nullable = false)
    private Double totalCost;

    @Column(name = "payment_method", nullable = false)
    private Long paymentMethod;

    @Column(name = "boat_id", nullable = false)
    private Long boatId;

    @Column(name = "booking_status_id", nullable = false)
    private Long bookingStatusId;

    @ManyToOne
    @JoinColumn(name = "mooring_id")
    private Mooring mooring;


}
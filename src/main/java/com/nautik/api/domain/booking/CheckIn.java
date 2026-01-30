package com.nautik.api.domain.booking;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "check_in")
public class CheckIn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "check_in_time", nullable = false)
    private LocalTime checkInTime;

    @Column(name = "check_out_time", nullable = false)
    private LocalTime checkOutTime;

    @Column(name = "documentation_provided", nullable = false)
    private Long documentationProvided;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;


}
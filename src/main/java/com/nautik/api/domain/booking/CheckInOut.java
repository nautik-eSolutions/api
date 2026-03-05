package com.nautik.api.domain.booking;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "check_in_out")
public class CheckInOut {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "booking_id", nullable = false, unique = true)
    private Booking booking;

    @Column(name = "scheduled_checkin_time")
    private Date scheduledCheckinTime;

    @Column(name = "actual_checkin_time")
    private Date actualCheckinTime;

    @Column(name = "has_checked_in", nullable = false)
    private Boolean hasCheckedIn = false;

    @Column(name = "scheduled_checkout_time")
    private Date scheduledCheckoutTime;

    @Column(name = "actual_checkout_time")
    private Date actualCheckoutTime;

    @Column(name = "has_checked_out", nullable = false)
    private Boolean hasCheckedOut = false;

}
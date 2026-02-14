package com.nautik.api.domain.moorings;

import com.nautik.api.domain.booking.Booking;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "mooring")
public class Mooring {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "number", nullable = false)
    private Long number;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "mooring_category_id", nullable = false, referencedColumnName = "id")
    private MooringCategory mooringCategory;

    @OneToMany(mappedBy = "mooring")
    private List<Booking> bookings;
}
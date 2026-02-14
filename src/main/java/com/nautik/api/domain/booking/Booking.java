package com.nautik.api.domain.booking;

import com.nautik.api.domain.Boat;
import com.nautik.api.domain.moorings.Mooring;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

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
    private Date startDate;

    @Column(name = "end_date", nullable = false)
    private Date endDate;

    @Column(name = "total_cost", nullable = false)
    private Double totalCost;

    @ManyToOne
    @JoinColumn(name = "boat_id")
    private Boat boat;

    @ManyToOne
    @JoinColumn(name = "mooring_id")
    private Mooring mooring;



    public Booking( Date startDate, Date endDate, Double totalCost, Boat boat, Mooring mooring){
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalCost = totalCost;
        this.boat = boat;
        this.mooring = mooring;

    }




}
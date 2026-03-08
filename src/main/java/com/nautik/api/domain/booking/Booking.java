package com.nautik.api.domain.booking;

import com.nautik.api.domain.Boat;
import com.nautik.api.domain.moorings.Mooring;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Column(name = "end_date", nullable = false)
    private Date endDate;

    @Column(name = "total_cost", nullable = false)
    private Double totalCost;

    @ManyToOne
    @JoinColumn(name = "boat_id")
    private Boat boat;

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", updatable = false)
    private Date createdAt;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;



    @ManyToOne
    @JoinColumn(name = "mooring_id")
    private Mooring mooring;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private BookingStatus status = BookingStatus.PENDING;

    @Column(name = "order_number", unique = true)
    private String orderNumber;

    @OneToOne(mappedBy = "booking")
    private Payment payment;


    public Booking( Date startDate, Date endDate, Double totalCost, Boat boat, Mooring mooring,String orderNumber){
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalCost = totalCost;
        this.boat = boat;
        this.mooring = mooring;
        this.orderNumber = orderNumber;

    }

    public Booking(Date startDate, Date endDate){
        this.startDate = startDate;
        this.endDate = endDate;
    }

}
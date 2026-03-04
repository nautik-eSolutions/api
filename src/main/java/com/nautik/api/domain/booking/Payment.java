package com.nautik.api.domain.booking;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "payment")
@NoArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "issue_date", nullable = false)
    private Date issueDate;

    @Column(name = "total_amount", nullable = false)
    private Double totalAmount;

    @OneToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;

    private String billingAddress;

    private String city;
    private String country;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PaymentStatus status = PaymentStatus.PENDING;

    @PrePersist
    public void onCreate(){
        this.issueDate = new Date();
    }


    public Payment(Double totalAmount, String billingAddress,String city, String country,PaymentStatus status){
        this.totalAmount = totalAmount;
        this.billingAddress = billingAddress;
        this.city = city;
        this.country = country;
        this.status =status;
    }


}
package com.nautik.api.domain.booking;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "issue_date", nullable = false)
    private LocalDate issueDate;

    @Column(name = "total_amount", nullable = false)
    private Double totalAmount;

    @Column(name = "last_numbers_of_card", nullable = false)
    private Long lastNumbersOfCard;


}
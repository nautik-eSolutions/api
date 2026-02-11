package com.nautik.api.domain;

import com.nautik.api.domain.booking.Booking;
import com.nautik.api.domain.users.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "boat")
public class Boat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "registry_number", nullable = false, length = 20)
    private String registryNumber;

    @Column(name = "length", nullable = false)
    private Double length;

    @Column(name = "beam", nullable = false)
    private Double beam;

    @Column(name = "draft", nullable = false)
    private Double draft;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "boat_type_id", nullable = false)
    private BoatType boatType;


    @OneToMany(mappedBy = "boat")
    private List<Booking> bookings;



    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
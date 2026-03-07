package com.nautik.api.domain.moorings;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "mooring_incident")
public class MooringIncident {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "mooring_id", nullable = false)
    private Mooring mooring;

    @NotNull
    @Column(name = "description", nullable = false)
    private Long mooringStatus;

    @NotNull
    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @NotNull
    @Column(name = "end_date", nullable = false)
    private Date endDate;


}
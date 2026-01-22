package com.nautik.api.domain.moorings;

import com.nautik.api.domain.Zone;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "mooring_category")
public class MooringCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;


//    @MapsId
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "zone_id", nullable = false)
//    private Zone zone;

    @Column(name = "max_length", nullable = false)
    private Long maxLength;

    @Column(name = "max_beam", nullable = false)
    private Long maxBeam;


}
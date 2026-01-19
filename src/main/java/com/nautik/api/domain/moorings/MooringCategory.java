package com.nautik.api.domain.moorings;

import com.nautik.api.domain.Zone;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "mooring_category")
public class MooringCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "zone_id")
    private Zone zone;

    private Integer maxLength;
    private Integer maxBeam;

}

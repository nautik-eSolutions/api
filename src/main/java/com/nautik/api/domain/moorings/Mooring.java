package com.nautik.api.domain.moorings;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="mooring")
public class Mooring {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer number;


}

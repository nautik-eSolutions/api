package com.nautik.api.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "port_image")
public class PortImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "port_id", nullable = false)
    private Port port;

    @Column(name = "image_key", nullable = false)
    private String imageKey;

    public PortImage(Port port, String imageKey) {
        this.port = port;
        this.imageKey = imageKey;
    }
}
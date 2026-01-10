package com.nautik.api.domain.users;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@Embeddable
public class PortAdminId implements Serializable {
    private static final long serialVersionUID = -5054281453539127084L;
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "admin_id", nullable = false)
    private Integer adminId;


}
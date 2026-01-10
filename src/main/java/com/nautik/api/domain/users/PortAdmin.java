package com.nautik.api.domain.users;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "port_admin")
public class PortAdmin {
    @EmbeddedId
    private PortAdminId id;

    @MapsId("adminId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "admin_id", nullable = false, referencedColumnName = "id")
    private Admin admin;


}
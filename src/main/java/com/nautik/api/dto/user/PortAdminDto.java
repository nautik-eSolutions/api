package com.nautik.api.dto.user;

import com.nautik.api.domain.users.PortAdminId;
import lombok.Value;

import java.io.Serializable;


@Value
public class PortAdminDto implements Serializable {
    PortAdminId id;
    AdminDto admin;
}
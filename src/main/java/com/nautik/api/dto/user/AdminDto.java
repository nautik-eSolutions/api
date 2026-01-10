package com.nautik.api.dto.user;

import com.nautik.api.domain.users.AdminId;
import lombok.Value;

import java.io.Serializable;


@Value
public class AdminDto implements Serializable {
    AdminId id;
    UserDto user;
}
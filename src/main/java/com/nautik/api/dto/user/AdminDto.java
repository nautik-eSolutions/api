package com.nautik.api.dto.user;

import lombok.Value;

import java.io.Serializable;


@Value
public class AdminDto implements Serializable {
    Long id;
    UserDto user;
}
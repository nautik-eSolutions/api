package com.nautik.api.dto.user;

import lombok.Value;

import java.io.Serializable;


@Value
public class OwnerDto implements Serializable {
    Integer id;
    PersonDto person;
}
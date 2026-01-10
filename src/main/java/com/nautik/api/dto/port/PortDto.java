package com.nautik.api.dto.port;

import lombok.Value;

import java.io.Serializable;


@Value
public class PortDto implements Serializable {
    Integer id;
    String name;
}
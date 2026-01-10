package com.nautik.api.dto.location;

import com.nautik.api.dto.port.PortDto;
import lombok.Value;

import java.io.Serializable;


@Value
public class ZoneDto implements Serializable {
    Integer id;
    String name;
    String description;
    PortDto port;
}
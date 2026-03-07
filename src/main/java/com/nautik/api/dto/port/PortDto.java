package com.nautik.api.dto.port;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PortDto implements Serializable {
    Integer id;
    String name;
    String cityName;
    String companyName;
}
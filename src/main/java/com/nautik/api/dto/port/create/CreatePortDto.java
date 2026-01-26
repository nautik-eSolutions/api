package com.nautik.api.dto.port.create;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
@AllArgsConstructor
public class CreatePortDto {
    String name;
    String cityName;
    String companyName;
}

package com.nautik.api.dto.roles;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class RoleCreateDto {
    String name;
    String description;
    //List for the capabilities id if each role, has to be passed as an array

    @JsonProperty("capabilities")
    List<Integer> capabilities;
}

package com.nautik.api.dto.roles;

import lombok.Data;

import java.util.List;

@Data
public class RoleDto {

    String name;
    String description;
    List<String> capabilitiesName;



}

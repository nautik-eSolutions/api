package com.nautik.api.dto.roles;


import lombok.Data;

import java.util.List;

@Data
public class RoleCreateDto {
    String name;
    String description;
    //List for the capabilities id if each role, has to be passed as an array
    List<Integer> capabilities;
}

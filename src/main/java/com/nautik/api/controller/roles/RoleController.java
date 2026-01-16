package com.nautik.api.controller.roles;

import com.nautik.api.service.roles.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/roles/")
public class RoleController {

    @Autowired
    private RoleService roleService;



}

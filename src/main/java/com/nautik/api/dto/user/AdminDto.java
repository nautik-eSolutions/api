package com.nautik.api.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.nautik.api.domain.users.Admin;
import lombok.NoArgsConstructor;
import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminDto {

    @JsonProperty("id")
    Long id;

    @JsonProperty("user_id")
    Long userId;

    public AdminDto(Admin admin){
        this.id= admin.getId();
        this.userId=(long) admin.getUser().getId();
    }





}
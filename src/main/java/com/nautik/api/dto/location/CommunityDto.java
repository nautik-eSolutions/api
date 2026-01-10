package com.nautik.api.dto.location;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.io.Serializable;


@Value
public class CommunityDto implements Serializable {
    Integer id;
    String name;
}
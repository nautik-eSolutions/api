package com.nautik.api.dto.user;

import lombok.Value;

import java.io.Serializable;
import java.time.LocalDate;

@Value
public class PersonDto implements Serializable {
    Integer id;
    String firstName;
    String lastName;
    String identificationDocument;
    LocalDate birthDate;
}
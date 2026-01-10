package com.nautik.api.domain.users;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
@Embeddable
public class CompanyAdminId implements Serializable {


    private static final long serialVersionUID = 3116738835102643964L;
}
package com.nautik.api.domain;

@lombok.Getter
@lombok.Setter@jakarta.persistence.Entity
@jakarta.persistence.Table(name = "boat_type")
public class BoatType {
@jakarta.persistence.Id
@jakarta.persistence.GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
@jakarta.persistence.Column(name = "id", nullable = false)
private java.lang.Integer id;

@jakarta.persistence.Column(name = "name", nullable = false)
private java.lang.Integer name;



}
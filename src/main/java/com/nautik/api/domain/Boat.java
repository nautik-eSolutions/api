package com.nautik.api.domain;

@lombok.Getter
@lombok.Setter@jakarta.persistence.Entity
@jakarta.persistence.Table(name = "boat")
public class Boat {
@jakarta.persistence.Id
@jakarta.persistence.GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
@jakarta.persistence.Column(name = "id", nullable = false)
private java.lang.Integer id;

@jakarta.persistence.Column(name = "name", nullable = false)
private java.lang.String name;

@jakarta.persistence.Column(name = "registry_number", nullable = false, length = 20)
private java.lang.String registryNumber;

@jakarta.persistence.Column(name = "length", nullable = false)
private java.lang.Double length;

@jakarta.persistence.Column(name = "beam", nullable = false)
private java.lang.Double beam;

@jakarta.persistence.Column(name = "draft", nullable = false)
private java.lang.Double draft;

@jakarta.persistence.ManyToOne(fetch = jakarta.persistence.FetchType.LAZY, optional = false)
@org.hibernate.annotations.OnDelete(action = org.hibernate.annotations.OnDeleteAction.CASCADE)
@jakarta.persistence.JoinColumn(name = "boat_type_id", nullable = false)
private com.nautik.api.domain.BoatType boatType;

@jakarta.persistence.ManyToOne(fetch = jakarta.persistence.FetchType.LAZY, optional = false)
@org.hibernate.annotations.OnDelete(action = org.hibernate.annotations.OnDeleteAction.CASCADE)
@jakarta.persistence.JoinColumn(name = "user_id", nullable = false)
private com.nautik.api.domain.User user;



}
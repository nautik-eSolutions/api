package com.nautik.api.service.users;

import lombok.Data;

@Data
public class GoogleIdTokenInfo {

    private String email;
    private Boolean emailVerified;
    private String subject;
    private String name;
    private String picture;



    public GoogleIdTokenInfo(String email, Boolean emailVerified, String subject, String name, String picture){
        this.email=email;
        this.emailVerified=emailVerified;
        this.subject=subject;
        this.name=name;
        this.picture=picture;
    }

}

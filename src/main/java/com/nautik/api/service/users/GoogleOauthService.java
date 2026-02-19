package com.nautik.api.service.users;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.auth.oauth2.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Service
public class GoogleOauthService {




    private final GoogleIdTokenVerifier verifier;

    public GoogleOauthService(@Value("${application.security.oauth.google.client-id}") String clientId) {

        if (clientId == null || clientId.isBlank()) {
            throw new IllegalStateException("Falta configurar application.security.oauth.google.client-id");
        }

        this.verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), GsonFactory.getDefaultInstance())
                .setAudience(Collections.singletonList(clientId))
                .build();
    }

    public GoogleIdTokenInfo verifyIdToken(String idToken) {
        if (idToken == null || idToken.isBlank()) {
            throw new BadCredentialsException("Falta l'id token de Google");
        }

        try {
            GoogleIdToken verifiedIdToken = verifier.verify(idToken);
            if (verifiedIdToken == null) {
                throw new BadCredentialsException("L'id token de Google no és vàlid");
            }

            Payload payload = verifiedIdToken.getPayload();
            String email = payload.getEmail();
            if (email == null || email.isBlank()) {
                throw new BadCredentialsException("L'id token de Google no conté email");
            }

            Boolean emailVerified = payload.getEmailVerified();
            if (emailVerified != null && !emailVerified) {
                throw new BadCredentialsException("El correu de Google no està verificat");
            }

            return new GoogleIdTokenInfo(
                    email,
                    emailVerified,
                    payload.getSubject(),
                    (String) payload.get("name"),
                    (String) payload.get("picture")
            );
        } catch (GeneralSecurityException | IOException ex) {
            throw new BadCredentialsException("No s'ha pogut verificar l'id token de Google");
        }
    }
}
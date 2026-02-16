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
        // El clientId és el valor que Google assigna a la nostra aplicació OAuth.
        // Si no existeix, no podem validar que el token hagi estat emès per a aquesta app.
        if (clientId == null || clientId.isBlank()) {
            throw new IllegalStateException("Falta configurar application.security.oauth.google.client-id");
        }

        // Construïm un verificador reutilitzable que validarem a cada login.
        // El Builder demana dos components base:
        // 1) NetHttpTransport: client HTTP que el verificador utilitza internament
        //    per descarregar claus públiques de Google i comprovar la signatura JWT.
        // 2) GsonFactory: parser JSON per interpretar metadades i contingut del token.
        //
        // Un cop definit el transport i el parser, fixem "l'audiència" esperada:
        // setAudience(...) limita els tokens vàlids només als que tinguin "aud" = clientId.
        // Això evita acceptar tokens emesos per a una altra aplicació.
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
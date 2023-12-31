package com.example.springsecurityjwtsecurity.Security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtil {
@Value("${jwt-secret}")
    private String secret;
    // Method to sign and create a JWT using the injected secret
public String generateToken(String email)throws JWTCreationException,IllegalArgumentException {
    return JWT.create()
            .withSubject("User Details")
            .withClaim("email",email)
            .withIssuedAt(new Date())
            .withIssuer("YOUR APPLICATION/PROJECT/COMPANY NAME")
            .sign(Algorithm.HMAC256(secret));

}

public String validateTokenAndRetrieveSubject(String token)throws JWTVerificationException {
    JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
            .withSubject("User Details")
            .withIssuer("YOUR APPLICATION/PROJECT/COMPANY NAME")
            .build();
    DecodedJWT jwt = verifier.verify(token);
    return jwt.getClaim("email").asString();
}


}

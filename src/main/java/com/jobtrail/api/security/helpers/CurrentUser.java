package com.jobtrail.api.security.helpers;

import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.text.ParseException;
import java.util.Map;

public class CurrentUser {
    public static  Map<String, ?> GetClaims() throws ParseException {
        String token = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();

        JWT jwt = JWTParser.parse(token);
        JWTClaimsSet jwtClaimSet = jwt.getJWTClaimsSet();

        return jwtClaimSet.getClaims();
    }

    public static Long getId() {
        try {
            return Long.parseLong(GetClaims().get("userId").toString());
        } catch (Exception ex) {
            return null;
        }
    }

    public static String getUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return authentication.getName();
    }
}

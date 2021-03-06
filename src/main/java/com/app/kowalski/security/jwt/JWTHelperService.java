package com.app.kowalski.security.jwt;

import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class JWTHelperService {

    @Value("${kowalski.token.expiration ?:26000000}")
    private long EXPIRATION_TIME;
    @Value("${kowalski.token.secret ?:MySecret}")
    private String SECRET;
    private final String TOKEN_PREFIX = "Bearer";
    private final String HEADER_STRING = "Authorization";


    public long getExpirationTime() {
        return EXPIRATION_TIME;
    }

    public String getSecret() {
        return SECRET;
    }

    public String getTokenPrefix() {
        return TOKEN_PREFIX;
    }

    public String getHeaderString() {
        return HEADER_STRING;
    }

    public void addAuthentication(HttpServletResponse response, Authentication auth) {
        Gson gson = new Gson();
        DefaultClaims claim = new DefaultClaims();
        claim.setSubject(auth.getName());
        Date expiration = new Date(System.currentTimeMillis() + EXPIRATION_TIME);

        String JWT = Jwts.builder()
                .setClaims(claim)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();

        response.setHeader("expires", Long.toString(expiration.getTime()));
        response.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
    }

    public Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        Gson gson = new Gson();

        if (token != null) {
            try {
                Claims claim = Jwts.parser()
                        .setSigningKey(SECRET)
                        .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                        .getBody();
                String user = claim.getSubject();
                List<String> authorities = gson.fromJson((String) claim.get("authorities"), List.class);

                if (user != null) {
                    return new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());
                }
            }catch (Exception e){
                //TODO LOG
            }
        }
        return null;
    }
}

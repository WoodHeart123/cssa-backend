package com.tencent.wxcloudrun.model;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class Jwtutil {
    private static final String CLAIM_KEY_UID = "sub";

    private static final String CLAIM_KEY_CREATED = "created";

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    @Value("${jwt.header}")
    private String tokenHeader;

    public String getUidFromToken(String token) {
        String uid;
        try {
            Claims claims = getClaimsFromToken(token);
            uid = claims.getSubject();
        } catch (Exception e) {
            uid = null;
        }
        return uid;
    }

    public Date getCreatedDateFromToken(String token) {
        Date created;
        try {
            Claims claims = getClaimsFromToken(token);
            created = new Date(((Long)claims.get("created")).longValue());
        } catch (Exception e) {
            created = null;
        }
        return created;
    }

    public Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            Claims claims = getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = (Claims) Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    private Date generateExpirationDate() {
        long exp = System.currentTimeMillis() + this.expiration * 1000L;
        return new Date(exp);
    }

    private Boolean isTokenExpired(String token) {
        Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(Integer uid) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", uid);
        claims.put("created", new Date());
        return generateToken(claims);
    }

    public String generateToken(String uid) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", uid);
        claims.put("created", new Date());
        return generateToken(claims);
    }

    public String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512,this.secret)
                .compact();
    }

    public boolean isTokenValid(String token) {
        Claims claims = getClaimsFromToken(token);
        if (claims == null)
            return false;
        return !claims.getExpiration().before(new Date());
    }
}

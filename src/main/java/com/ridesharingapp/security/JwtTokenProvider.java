package com.ridesharingapp.security;

import com.ridesharingapp.utils.exceptions.JwtAuthenticationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String JWT_TOKEN_SECRET_KEY;

    @Value("${jwt.expirationMs}")
    private long JWT_EXPIRATION_MS;

    @Value("${jwt.header}")
    private String AUTHORIZATION_HEADER;

    @Value("${jwt.prefix}")
    private String PREFIX_BEARER;

    @PostConstruct
    protected void init() {
        JWT_TOKEN_SECRET_KEY = Base64.getEncoder().encodeToString(JWT_TOKEN_SECRET_KEY.getBytes());
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(JWT_TOKEN_SECRET_KEY).parseClaimsJws(token).getBody();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims =
                Jwts
                        .parser()
                        .setSigningKey(JWT_TOKEN_SECRET_KEY)
                        .parseClaimsJws(token)
                        .getBody();
        return claimsResolver.apply(claims);
    }
    public String generateToken(int userId, String password, String email, String userRole) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION_MS);

        return Jwts.builder()
                .setSubject(email)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, JWT_TOKEN_SECRET_KEY)
                .claim("password", password)
                .claim("userId", userId)
                .claim("userRole",userRole)
                .compact();
    }

    public boolean validateToken(String token) throws JwtAuthenticationException {
        try {
            Jwts.parser().setSigningKey(JWT_TOKEN_SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtAuthenticationException("JWT token is expired or invalid", HttpStatus.UNAUTHORIZED);
        }
    }

    public Authentication getAuthentication(String token) {

        String email = getClaimFromToken(token, Claims::getSubject);
        String role = this.getClaimFromToken(token, key -> key.get("roles", String.class));

        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role);
        grantedAuthorities.add(grantedAuthority);

        UserDetails userDetails = new User(email, "[PROTECTED]", grantedAuthorities);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }


    public String resolveToken(HttpServletRequest request) {
        String header = request.getHeader(AUTHORIZATION_HEADER);
        return header == null ? null : header.replaceAll(PREFIX_BEARER, "").trim();
    }
}

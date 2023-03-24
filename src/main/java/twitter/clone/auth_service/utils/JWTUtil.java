package twitter.clone.auth_service.utils;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import twitter.clone.auth_service.exception.JWTTokenMalformedException;
import twitter.clone.auth_service.exception.JWTTokenMissingException;

import java.util.Date;

@Component
public class JWTUtil {
    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.token.validity}")
    private long tokenValidity;

    public Claims getClaims(final String token) {
        try {
            Claims body = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
            return body;
        } catch (Exception e) {
            System.out.println(e.getMessage() + " => " + e);
        }
        return null;
    }

    public String generateToken(String id) {
        Claims claims = Jwts.claims().setSubject(id);
        long nowMillis = System.currentTimeMillis();
        long expMillis = nowMillis + tokenValidity;
        Date exp = new Date(expMillis);
        return Jwts.builder().setClaims(claims).setIssuedAt(new Date(nowMillis)).setExpiration(exp)
                .signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
    }

    public void validateToken(final String token) throws JWTTokenMalformedException, JWTTokenMissingException {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
        } catch (SignatureException ex) {
            throw new JWTTokenMalformedException("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            throw new JWTTokenMalformedException("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            throw new JWTTokenMalformedException("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            throw new JWTTokenMalformedException("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            throw new JWTTokenMissingException("JWT claims string is empty.");
        }
    }
}

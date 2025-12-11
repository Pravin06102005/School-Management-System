package School.Management.School.Management.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtUtil {

    private final Key key= Keys.secretKeyFor(SignatureAlgorithm.HS256);

    //Generate token

    public String generateToken(String username, UUID schoolId){
        return Jwts.builder()
                .setSubject(username)
                .claim("schoolId",schoolId.toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+1000L*60*60*24))//24 hrs
                .signWith(key)
                .compact();
    }


    // Extract username
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    // Extract schoolId
    public String extractSchoolId(String token) {
        return extractAllClaims(token).get("schoolId").toString();
    }

    //validate token
    public boolean isTokenValid(String token){
        try {
            extractAllClaims(token);
            return true;
        } catch (Exception e) {
            return  false;
        }
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}

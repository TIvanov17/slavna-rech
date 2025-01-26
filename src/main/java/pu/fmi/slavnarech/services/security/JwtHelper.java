package pu.fmi.slavnarech.services.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import javax.crypto.SecretKey;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtHelper {

  private static final String SECRET_KEY =
      "1b113cccdc603c55f5ccfee05f7e6ad6f98f986a6e51fed80cfcf8644050b1ea";

  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  public String generateToken(UserDetails userDetails) {
    return generateToken(new HashMap<>(), userDetails);
  }

  public String generateRefreshToken(UserDetails userDetails) {
    return generateRefreshToken(new HashMap<>(), userDetails);
  }

  public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
    return JWT.create()
        .withSubject(userDetails.getUsername())
        .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 50 * 24 * 24))
        .withIssuedAt(new Date(System.currentTimeMillis()))
        .sign(getAlgorithmKey());
  }

  public String generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails) {
    return JWT.create()
        .withSubject(userDetails.getUsername())
        .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 24 * 24))
        .withIssuedAt(new Date(System.currentTimeMillis()))
        .sign(getAlgorithmKey());
  }

  public boolean isTokenValid(String token, UserDetails userDetails) {
    String username = extractUsername(token);
    return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
  }

  public boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  public Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parser().verifyWith(getSignInKey()).build().parseSignedClaims(token).getPayload();
  }

  public DecodedJWT decodedToken(String token) {
    return JWT.require(getAlgorithmKey()).build().verify(token);
  }

  private SecretKey getSignInKey() {
    return Keys.hmacShaKeyFor(getDecoder());
  }

  private Algorithm getAlgorithmKey() {
    return Algorithm.HMAC256(getDecoder());
  }

  private byte[] getDecoder() {
    return Decoders.BASE64.decode(SECRET_KEY);
  }
}

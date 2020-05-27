package com.nikhra.common.AuthServer.jwt;

import static com.nikhra.common.AuthServer.jwt.JWTConstants.AUDIENCE;
import static com.nikhra.common.AuthServer.jwt.JWTConstants.CLIENT_ID;
import static com.nikhra.common.AuthServer.jwt.JWTUtils.getPrivateKey;

import com.nikhra.common.AuthServer.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.InvalidKeyException;
import java.io.IOException;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JWTGenerator implements Serializable {

  @Value("${jwt.privateKey.location}")
  private String privateKeyLocation;

  public String getJWT(User user) throws JWTException {
    try {
      var privateKey = getPrivateKey(privateKeyLocation);
      var now = Instant.now();
      return Jwts.builder()
          .setAudience(AUDIENCE)
          .setIssuedAt(Date.from(now))
          .setExpiration(Date.from(now.plus(1, ChronoUnit.DAYS)))
          .setIssuer(CLIENT_ID)
          .setSubject(user.getId().toString())
          .claim("provider", user.getProvider())
          .signWith(privateKey)
          .compact();
    } catch (NoSuchAlgorithmException ex) {
      throw new JWTException("Wrong key algorithm mentioned", ex);
    } catch (IOException ex) {
      throw new JWTException("Wrong key location mentioned", ex);
    } catch (InvalidKeySpecException ex) {
      throw new JWTException("Wrong key spec mentioned", ex);
    } catch (InvalidKeyException ex) {
      throw new JWTException("Wrong key mentioned", ex);
    }
  }
}

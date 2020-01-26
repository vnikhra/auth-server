package com.nikhra.docushare.AuthServer.jwt;

import static com.nikhra.docushare.AuthServer.jwt.JWTUtils.getPublicKey;
import static com.nikhra.docushare.AuthServer.jwt.JWTUtils.verifyJwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class JWTVerifier {

  @Value("${jwt.publicKey.location}")
  private String publicKeyLocation;

  public String getUserId(HttpServletRequest request) throws JWTException {
    return verifyJWT(resolveToken(request)).getName();
  }

  public UsernamePasswordAuthenticationToken verifyJWT(String tokenString)
      throws JWTException {
    try {
      var publicKey = getPublicKey(publicKeyLocation);
      var jwsClaims = verifyJwt(tokenString, publicKey);
      var userId = (String) jwsClaims.getBody().getSubject();
      return new UsernamePasswordAuthenticationToken(userId, null);
    } catch (NoSuchAlgorithmException ex) {
      throw new JWTException("Wrong key algorithm mentioned", ex);
    } catch (IOException ex) {
      throw new JWTException("Wrong key location mentioned", ex);
    } catch (InvalidKeySpecException ex) {
      throw new JWTException("Wrong key spec mentioned", ex);
    } catch (ExpiredJwtException ex) {
      throw new JWTException("JWT token expired", ex);
    } catch (UnsupportedJwtException ex) {
      throw new JWTException("JWT token unsupported", ex);
    } catch (MalformedJwtException ex) {
      throw new JWTException("JWT token invalid", ex);
    } catch (SignatureException ex) {
      throw new JWTException("JWT token signature invalid", ex);
    } catch (IllegalArgumentException ex) {
      throw new JWTException("JWT token empty or null", ex);
    }
  }

  public String resolveToken(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");
    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }
    return null;
  }

}

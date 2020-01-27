package com.nikhra.docushare.AuthServer.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import java.io.IOException;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import org.springframework.core.io.ClassPathResource;

public class JWTUtils {

  static PrivateKey getPrivateKey(String fileName)
      throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
    return KeyFactory.getInstance("RSA")
        .generatePrivate(
            new PKCS8EncodedKeySpec(
                Files.readAllBytes(new ClassPathResource(fileName).getFile().toPath())));
  }

  static PublicKey getPublicKey(String fileName)
      throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
    return KeyFactory.getInstance("RSA")
        .generatePublic(
            new X509EncodedKeySpec(
                Files.readAllBytes(new ClassPathResource(fileName).getFile().toPath())));
  }

  static Jws<Claims> verifyJwt(String jwtString, PublicKey key)
      throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException,
          SignatureException, IllegalArgumentException {
    return Jwts.parser().setSigningKey(key).parseClaimsJws(jwtString);
  }
}

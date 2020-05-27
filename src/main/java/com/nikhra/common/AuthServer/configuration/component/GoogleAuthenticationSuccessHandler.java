package com.nikhra.common.AuthServer.configuration.component;

import com.nikhra.common.AuthServer.dao.UserDAO;
import com.nikhra.common.AuthServer.jwt.JWTException;
import com.nikhra.common.AuthServer.jwt.JWTGenerator;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class GoogleAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

  @Autowired
  UserDAO userDAO;

  @Autowired
  JWTGenerator jwtGenerator;

  @Override
  public void onAuthenticationSuccess(
      HttpServletRequest request, HttpServletResponse response, Authentication authentication)
      throws IOException {
    System.out.println("Authenticated!!!");
    if (response.isCommitted()) {
      return;
    }
    var oidcUser = (DefaultOidcUser) authentication.getPrincipal();
    var user = userDAO.getUserByProviderUserId((String) oidcUser.getAttributes().get("sub"));
    var redirectUrl = "/redirect.html";
    try {
      var token = jwtGenerator.getJWT(user);
      redirectUrl = redirectUrl + "?token=" + token;
    } catch (JWTException e) {
      e.printStackTrace();
    }
    getRedirectStrategy().sendRedirect(request, response, redirectUrl);
  }
}

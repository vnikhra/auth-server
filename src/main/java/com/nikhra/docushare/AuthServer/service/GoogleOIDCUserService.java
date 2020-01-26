package com.nikhra.docushare.AuthServer.service;

import com.nikhra.docushare.AuthServer.constants.enums.SsoProviders;
import com.nikhra.docushare.AuthServer.dao.UserDAO;
import com.nikhra.docushare.AuthServer.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

@Service
public class GoogleOIDCUserService extends OidcUserService {

  @Autowired
  private UserDAO userDAO;

  @Override
  public OidcUser loadUser(OidcUserRequest userRequest) {
    OidcUser oidcUser = super.loadUser(userRequest);
    var attributes = oidcUser.getAttributes();
    var currentRecord = userDAO.getUserByProviderUserId((String) attributes.get("sub"));
    if (currentRecord == null) {
      var user = new User(
          (String) attributes.get("name"),
          (String) attributes.get("email"),
          SsoProviders.GOOGLE,
          (String) attributes.get("sub"),
          (String) attributes.get("picture")
      );
      userDAO.createUser(user);
    } else {
      System.out.println(currentRecord.getEmail());
    }
    return oidcUser;
  }
}

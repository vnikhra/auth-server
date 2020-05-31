package com.nikhra.common.AuthServer.configuration;

import com.nikhra.common.AuthServer.configuration.component.GoogleAuthenticationSuccessHandler;
import com.nikhra.common.AuthServer.service.GoogleOIDCUserService;
import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity(debug = true)
@Order(3)
public class SocialSecurityRedirectConfig extends WebSecurityConfigurerAdapter {

  @Autowired GoogleOIDCUserService googleOIDCUserService;
  @Autowired GoogleAuthenticationSuccessHandler googleAuthenticationSuccessHandler;
  @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
  private String redirectUrl;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.antMatcher(new URI(redirectUrl).getPath())
        .authorizeRequests()
        .anyRequest()
        .authenticated()
        .and()
        .oauth2Login()
        .redirectionEndpoint()
        .baseUri(new URI(redirectUrl).getPath())
        .and()
        .userInfoEndpoint()
        .oidcUserService(googleOIDCUserService)
        .and()
        .successHandler(googleAuthenticationSuccessHandler);
  }
}

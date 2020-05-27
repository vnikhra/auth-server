package com.nikhra.common.AuthServer.configuration;

import com.nikhra.common.AuthServer.service.GoogleOIDCUserService;
import com.nikhra.common.AuthServer.configuration.component.GoogleAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity(debug = true)
@Order(2)
public class SocialSecurityConfig extends WebSecurityConfigurerAdapter {

  @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
  private String redirectUrl;

  @Autowired
  GoogleOIDCUserService googleOIDCUserService;

  @Autowired GoogleAuthenticationSuccessHandler googleAuthenticationSuccessHandler;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.antMatcher("/oauth*/**")
        .authorizeRequests()
        .anyRequest()
        .authenticated()
        .and()
        .oauth2Login()
        .redirectionEndpoint()
        .baseUri("/oauth/*")
        .and()
        .userInfoEndpoint()
        .oidcUserService(googleOIDCUserService)
        .and()
        .successHandler(googleAuthenticationSuccessHandler);
  }
}

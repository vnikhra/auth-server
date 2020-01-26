package com.nikhra.docushare.AuthServer.configuration;

import com.nikhra.docushare.AuthServer.configuration.component.GoogleAuthenticationSuccessHandler;
import com.nikhra.docushare.AuthServer.service.GoogleOIDCUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@Order(2)
public class SocialSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  GoogleOIDCUserService googleOIDCUserService;

  @Autowired
  GoogleAuthenticationSuccessHandler googleAuthenticationSuccessHandler;

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    http.antMatcher("/oauth*/**")
        .authorizeRequests()
        .anyRequest().authenticated()
        .and().oauth2Login()
        .redirectionEndpoint()
        .baseUri("/oauth/*")
        .and().userInfoEndpoint()
        .oidcUserService(googleOIDCUserService)
        .and().successHandler(googleAuthenticationSuccessHandler);
  }
}

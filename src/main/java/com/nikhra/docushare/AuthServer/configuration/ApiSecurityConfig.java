package com.nikhra.docushare.AuthServer.configuration;

import com.google.common.collect.ImmutableList;
import com.nikhra.docushare.AuthServer.jwt.JWTException;
import com.nikhra.docushare.AuthServer.jwt.JWTVerifier;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.OncePerRequestFilter;

@Configuration
@Order(1)
public class ApiSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired JWTVerifier jwtVerifier;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf()
        .disable()
        .antMatcher("/api/**")
        .cors()
        .configurationSource(corsConfigurationSource())
        .and()
        .exceptionHandling()
        .and()
        .authorizeRequests()
        .anyRequest()
        .authenticated()
        .and()
        .addFilterBefore(
            new OncePerRequestFilter() {
              @Override
              protected void doFilterInternal(
                  HttpServletRequest request, HttpServletResponse response, FilterChain chain)
                  throws IOException, ServletException {
                try {
                  var authentication = jwtVerifier.verifyJWT(jwtVerifier.resolveToken(request));
                  SecurityContextHolder.getContext().setAuthentication(authentication);
                } catch (JWTException ex) {
                  SecurityContextHolder.clearContext();
                  response.sendError(403, ex.getMessage());
                  System.out.println(ex);
                  return;
                }
                chain.doFilter(request, response);
              }
            },
            UsernamePasswordAuthenticationFilter.class)
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authenticationProvider(
            new AuthenticationProvider() {
              @Override
              public Authentication authenticate(Authentication authentication)
                  throws AuthenticationException {
                System.out.println(authentication.getName());
                return new UsernamePasswordAuthenticationToken(authentication.getName(), "");
              }

              @Override
              public boolean supports(Class<?> authentication) {
                return authentication.equals(UsernamePasswordAuthenticationToken.class);
              }
            });
  }

  private CorsConfigurationSource corsConfigurationSource() {
    final CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(ImmutableList.of("*"));
    configuration.setAllowedMethods(
        ImmutableList.of("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH"));
    configuration.setAllowCredentials(true);
    configuration.setAllowedHeaders(
        ImmutableList.of("Authorization", "Cache-Control", "Content-Type"));
    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }
}

package com.nikhra.docushare.AuthServer.controller;

import com.nikhra.docushare.AuthServer.jwt.JWTException;
import com.nikhra.docushare.AuthServer.jwt.JWTVerifier;
import com.nikhra.docushare.AuthServer.model.User;
import com.nikhra.docushare.AuthServer.service.UserService;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/user")
public class UserController {

  @Autowired
  UserService userService;

  @Autowired
  JWTVerifier jwtVerifier;

  @GetMapping
  public User getUser(HttpServletRequest request, HttpServletResponse response)
      throws JWTException, IOException {
    var id = jwtVerifier.getUserId(request);
    var user = userService.getUserById(id);
    if (user == null) {
      response.sendError(404, "User not found");
      return null;
    }
    return user;
  }
}

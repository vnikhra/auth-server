package com.nikhra.common.AuthServer.controller;

import com.nikhra.common.AuthServer.jwt.JWTVerifier;
import com.nikhra.common.AuthServer.model.User;
import com.nikhra.common.AuthServer.service.UserService;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/user")
public class UserController {

  @Autowired UserService userService;

  @Autowired
  JWTVerifier jwtVerifier;

  @GetMapping
  public User getUser(HttpServletResponse response) throws IOException {
    var user =
        userService.getUserById(SecurityContextHolder.getContext().getAuthentication().getName());
    if (user == null) {
      response.sendError(404, "User not found");
      return null;
    }
    return user;
  }

  @GetMapping
  @RequestMapping("/all")
  public List<User> getAllUsers(HttpServletResponse response) throws IOException {
    var user =
        userService.getUserById(SecurityContextHolder.getContext().getAuthentication().getName());
    if (user == null) {
      response.sendError(404, "Users not found");
      return null;
    }
    return userService.getAllUsers();
  }
}

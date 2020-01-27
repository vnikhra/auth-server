package com.nikhra.docushare.AuthServer.service;

import com.nikhra.docushare.AuthServer.dao.UserDAO;
import com.nikhra.docushare.AuthServer.model.User;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired UserDAO userDAO;

  public User getUserById(String id) {
    return userDAO.getUserById(UUID.fromString(id));
  }

  public List<User> getAllUsers() {
    return userDAO.getAllUsers().stream()
        .map(user -> new User(user.getId(), user.getName(), user.getEmail()))
        .collect(Collectors.toList());
  }
}

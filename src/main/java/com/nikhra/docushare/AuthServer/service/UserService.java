package com.nikhra.docushare.AuthServer.service;

import com.nikhra.docushare.AuthServer.dao.UserDAO;
import com.nikhra.docushare.AuthServer.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  UserDAO userDAO;

  public User getUserById(String id) {
    return userDAO.getUserById(id);
  }
}

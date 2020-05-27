package com.nikhra.common.AuthServer.dao;

import com.nikhra.common.AuthServer.entity.UserEntity;
import com.nikhra.common.AuthServer.model.User;
import com.nikhra.common.AuthServer.repository.UserRepository;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserDAO {

  @Autowired private UserRepository userRepository;

  private static User getUserByUserEntity(UserEntity userEntity) {
    if (userEntity == null) {
      return null;
    }
    return new User(
        userEntity.getId(),
        userEntity.getName(),
        userEntity.getEmail(),
        userEntity.getProvider(),
        userEntity.getProviderUserId(),
        userEntity.getProfileImageUrl());
  }

  public User createUser(User user) {
    return getUserByUserEntity(
        userRepository.save(
            new UserEntity(
                user.getName(),
                user.getEmail(),
                user.getProvider(),
                user.getProviderUserId(),
                user.getProfileImageUrl())));
  }

  public User getUserByProviderUserId(String providerUserId) {
    return getUserByUserEntity(userRepository.findByProviderUserId(providerUserId));
  }

  public User getUserById(UUID id) {
    return getUserByUserEntity(userRepository.findById(id).orElse(null));
  }

  public List<User> getAllUsers() {
    return StreamSupport.stream(userRepository.findAll().spliterator(), false)
        .map(UserDAO::getUserByUserEntity)
        .collect(Collectors.toList());
  }
}

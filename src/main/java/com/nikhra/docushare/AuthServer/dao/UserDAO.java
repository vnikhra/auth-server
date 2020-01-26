package com.nikhra.docushare.AuthServer.dao;

import com.nikhra.docushare.AuthServer.entity.UserEntity;
import com.nikhra.docushare.AuthServer.model.User;
import com.nikhra.docushare.AuthServer.repository.UserRepository;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserDAO {

  @Autowired
  private UserRepository userRepository;

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
        userEntity.getProfileImageUrl()
    );
  }

  public User createUser(User user) {
    return getUserByUserEntity(userRepository.save(new UserEntity(
        user.getName(),
        user.getEmail(),
        user.getProvider(),
        user.getProviderUserId(),
        user.getProfileImageUrl()
    )));
  }

  public User getUserByProviderUserId(String providerUserId) {
    return getUserByUserEntity(userRepository.findByProviderUserId(providerUserId));
  }

  public User getUserById(String id) {
    return getUserByUserEntity(userRepository.findById(UUID.fromString(id))
        .orElse(null));
  }
}

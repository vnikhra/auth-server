package com.nikhra.common.AuthServer.repository;

import com.nikhra.common.AuthServer.entity.UserEntity;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, UUID> {
  UserEntity findByProviderUserId(String providerUserId);
}

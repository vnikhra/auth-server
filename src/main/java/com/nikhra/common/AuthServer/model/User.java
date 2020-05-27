package com.nikhra.common.AuthServer.model;

import com.nikhra.common.AuthServer.constants.enums.SsoProviders;
import java.io.Serializable;
import java.util.UUID;

public class User implements Serializable {

  private UUID id;
  private String name;
  private String email;
  private SsoProviders provider;
  private String providerUserId;
  private String profileImageUrl;

  public User(
      UUID id,
      String name,
      String email,
      SsoProviders provider,
      String providerUserId,
      String profileImageUrl) {
    this(name, email, provider, providerUserId, profileImageUrl);
    this.id = id;
  }

  public User(
      String name,
      String email,
      SsoProviders provider,
      String providerUserId,
      String profileImageUrl) {
    this.name = name;
    this.email = email;
    this.provider = provider;
    this.providerUserId = providerUserId;
    this.profileImageUrl = profileImageUrl;
  }

  public User(UUID id, String name, String email) {
    this.id = id;
    this.name = name;
    this.email = email;
  }

  public String getEmail() {
    return email;
  }

  public UUID getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public SsoProviders getProvider() {
    return provider;
  }

  public String getProviderUserId() {
    return providerUserId;
  }

  public String getProfileImageUrl() {
    return profileImageUrl;
  }
}

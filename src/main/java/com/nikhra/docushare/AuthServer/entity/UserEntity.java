package com.nikhra.docushare.AuthServer.entity;

import com.nikhra.docushare.AuthServer.constants.enums.SsoProviders;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "users")
public class UserEntity {

  @Id
  @GeneratedValue(generator = "UUID")
  @Type(type = "uuid-char")
  private UUID id;

  @Column
  private String name;

  @Column
  private String email;

  @Column
  @Enumerated(EnumType.STRING)
  private SsoProviders provider;

  @Column(name = "provider_user_id", unique = true)
  private String providerUserId;

  @Column(name = "profile_image")
  private String profileImageUrl;

  public UserEntity(UUID id, String name, String email, SsoProviders provider,
      String providerUserId, String profileImageUrl) {
    this(name, email, provider, providerUserId, profileImageUrl);
    this.id = id;
  }

  public UserEntity() {
  }

  public UserEntity(String name, String email, SsoProviders provider, String providerUserId,
      String profileImageUrl) {
    this.name = name;
    this.email = email;
    this.provider = provider;
    this.providerUserId = providerUserId;
    this.profileImageUrl = profileImageUrl;
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

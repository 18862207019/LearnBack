package com.dada.config;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;

import java.util.UUID;

/**
 * 解决同一username每次登陆access_token都相同的问题
 * @author HUPD
 */
public class RandomAuthenticationKeyGenerator implements AuthenticationKeyGenerator {

  @Override
  public String extractKey(OAuth2Authentication authentication) {
    return UUID.randomUUID().toString();
  }
}

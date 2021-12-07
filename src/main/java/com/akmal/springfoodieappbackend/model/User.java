package com.akmal.springfoodieappbackend.model;

import java.util.Map;

/**
 * <h1>User</h1>
 * The class encapsulates the User data extracted from the access token
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 12/11/2021 - 7:06 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
public record User(String userId, String username) {
  public static final String OKTA_ID_KEY = "uid";
  public static final String OKTA_USERNAME_KEY = "sub";

  /**
   * The method is a static factory that converts the attributes from the okta
   * access token to the {@code User} instance.
   * @param attributes a hashmap of attributes from the {@link org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken}
   * @return {@link User} instance
   */
  public static User fromOktaAttributes(Map<String, Object> attributes) {
    var username = (String) attributes.get(OKTA_USERNAME_KEY);
    var userId = (String)  attributes.get(OKTA_ID_KEY);

    return new User(userId, username);
  }
}

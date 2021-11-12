package com.akmal.springfoodieappbackend.service;

import com.akmal.springfoodieappbackend.exception.AuthException;
import com.akmal.springfoodieappbackend.model.User;
import com.akmal.springfoodieappbackend.repository.CustomerDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

/**
 * <h1>UserService</h1>
 * The class contains the logic of managing the users and their {@link com.akmal.springfoodieappbackend.model.CustomerDetails}
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 12/11/2021 - 7:05 PM
 * @project Spring Foodie App Backend
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
public class UserService {

  private final CustomerDetailsRepository customerDetailsRepository;

  /**
   * Method returns currently authenticated {@link User} object.
   * It maps okta token attributes to the custom user object.
   * @throws AuthException in case user is not authenticated or access token is not of
   * type {@link JwtAuthenticationToken}
   * @return {@link User} currently authenticated
   */
  public User getCurrentUser() {
    var authentication = SecurityContextHolder.getContext().getAuthentication();

    if (!authentication.isAuthenticated()) {
      throw new AuthException("Unable to get current user. The user is not authenticated");
    }

    if (authentication instanceof JwtAuthenticationToken token) {
      return User.fromOktaAttributes(token.getTokenAttributes());
    }

    throw new AuthException("The access token is not of the correct type");
  }
}

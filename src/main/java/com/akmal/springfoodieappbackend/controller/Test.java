package com.akmal.springfoodieappbackend.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test {

  @GetMapping("/test")
  @PreAuthorize("!hasRole('ZADMIN')")
  public Object test(@AuthenticationPrincipal OidcUser user) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    return authentication.getAuthorities();
  }
}

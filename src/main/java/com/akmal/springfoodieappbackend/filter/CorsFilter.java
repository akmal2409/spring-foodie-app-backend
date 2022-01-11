package com.akmal.springfoodieappbackend.filter;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * The filter class that gets triggered before any request that comes to the application. Adds
 * appropriate headers to the response.
 *
 * @author Akmal Alikhujaev
 * @version 1.0
 * @created 22/12/2021 - 20:26
 * @project Spring Foodie App Backend
 * @since 1.0
 */
@ConfigurationProperties(prefix = "cors")
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@Setter
@Profile("!test & !integrationTest")
public class CorsFilter extends OncePerRequestFilter {
  /** Indicates whether CORS filter is enabled */
  private boolean enabled = false;

  /** Allowed origin as a valid URL */
  private String origin = "*";

  /** Collection of allowed methods */
  private List<String> methods = List.of("GET", "POST", "DELETE", "PUT", "OPTIONS");

  /** Collection of allowed headers */
  private List<String> headers = List.of("*");

  /** Boolean to allow credentials */
  private boolean allowCredentials = true;

  /** Max age property */
  private String maxAge = "180";

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    if (this.enabled) {
      response.setHeader("Access-Control-Allow-Origin", this.origin);
      response.setHeader("Access-Control-Allow-Methods", String.join(",", this.methods));
      response.setHeader("Access-Control-Allow-Headers", String.join(",", this.headers));
      response.setHeader("Access-Control-Allow-Credentials", String.valueOf(this.allowCredentials));
      response.setHeader("Access-Control-Max-Age", this.maxAge);
    }

    filterChain.doFilter(request, response);
  }
}

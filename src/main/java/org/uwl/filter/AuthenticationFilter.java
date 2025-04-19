package org.uwl.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.uwl.entity.User;
import org.uwl.service.UserService;

import static org.uwl.entity.User.UserRole.USER;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthenticationFilter extends OncePerRequestFilter {

  private final UserService userService;

  /** This method will execute per request. */
  @Override
  protected void doFilterInternal(
      final HttpServletRequest request,
      @NonNull final HttpServletResponse response,
      @NonNull final FilterChain filterChain)
      throws IOException, ServletException {
    log.trace("doFilterInternal()");
    final String email = (String) request.getSession(true).getAttribute("LoggedInUserEmail");
    if (email != null) {
      final User userDB = userService.getUserByEmail(email);
      if (Objects.nonNull(userDB)) {
        String role = USER.equals(userDB.getRole()) ? "USER_ROLE" : "ADMIN_ROLE";
        final UsernamePasswordAuthenticationToken authentication =
            new UsernamePasswordAuthenticationToken(
                userDB, null, List.of(new SimpleGrantedAuthority(role)));
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    }
    filterChain.doFilter(request, response);
  }
}

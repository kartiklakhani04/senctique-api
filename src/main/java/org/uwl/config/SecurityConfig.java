package org.uwl.config;

import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.uwl.entity.User;
import org.uwl.filter.AuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@EnableAspectJAutoProxy
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {

  @Bean
  public SecurityFilterChain getSecurityFilterChain(
      final HttpSecurity httpSecurity,
      final AuthenticationManager authenticationManager,
      final CorsFilter corsFilter,
      final AuthenticationFilter authenticationFilter)
      throws Exception {
    log.trace("getSecurityFilterChain()");
    httpSecurity
        .csrf(csrf -> csrf.ignoringRequestMatchers("/**"))
        .authorizeHttpRequests(
            authorize ->
                authorize
                    .requestMatchers(
                        "/user/**",
                        "/product/**",
                        "/favorite/**",
                        "/contact/**",
                        "/cart/**",
                        "/address/**",
                        "/order/**",
                        "/subscription/**")
                    .permitAll()
                    .anyRequest()
                    .authenticated())
        .authenticationManager(authenticationManager)
        .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
        .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
    return httpSecurity.build();
  }

  @Bean
  public CorsFilter corsFilter() {
    log.trace("corsFilter()");
    final CorsConfiguration corsConfiguration = new CorsConfiguration();
    corsConfiguration.setAllowedOriginPatterns(
        List.of(
            "http://localhost:5173", "http://192.168.1.104:5173", "http://185.181.11.152:5173"));
    corsConfiguration.setAllowedMethods(
        List.of("GET", "POST", "PUT", "DELETE", "PATCH", "HEAD", "OPTIONS", "CONNECT", "TRACE"));
    corsConfiguration.setAllowedHeaders(List.of("*"));
    corsConfiguration.setAllowCredentials(Boolean.TRUE);
    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", corsConfiguration);
    return new CorsFilter(source);
  }

  @Bean
  public AuthenticationManager getAuthenticationManager(final PasswordEncoder passwordEncoder) {
    log.trace("getAuthenticationManager()");
    final AuthenticationProvider authenticationProvider =
        new AuthenticationProvider() {
          @Override
          public Authentication authenticate(final Authentication authentication)
              throws AuthenticationException {
            final User user = (User) authentication.getPrincipal();
            final String password = Objects.toString(authentication.getCredentials());
            if (Objects.isNull(user) || !passwordEncoder.matches(password, user.getPassword())) {
              throw new BadCredentialsException("BadCredentialsException");
            }
            final UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                    user, null, List.of(new SimpleGrantedAuthority("USER_ROLL")));
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            return authenticationToken;
          }

          @Override
          public boolean supports(final Class<?> authentication) {
            return authentication.equals(UsernamePasswordAuthenticationToken.class);
          }
        };
    return new ProviderManager(authenticationProvider);
  }

  @Bean
  public PasswordEncoder getPasswordEncoder() {
    log.trace("getPasswordEncoder()");
    return new BCryptPasswordEncoder();
  }
}

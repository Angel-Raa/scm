/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.github.angel.scm.configuration;

import com.github.angel.scm.service.impl.CustomOAuth2UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 *
 * @author aguero
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class HttpSecurityConfiguration {
    private final AuthenticationProvider provider;
    private final SessionRegistry sessionRegistry;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
    public HttpSecurityConfiguration(AuthenticationProvider provider, SessionRegistry sessionRegistry, CustomOAuth2UserService customOAuth2UserService, OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler) {
        this.provider = provider;

        this.sessionRegistry = sessionRegistry;
        this.customOAuth2UserService = customOAuth2UserService;
        this.oAuth2LoginSuccessHandler = oAuth2LoginSuccessHandler;
    }

    @Bean
    public SecurityFilterChain filterChain(@NotNull HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> {
            auth.requestMatchers("/authentication/**", "/home/**", "/css/**", "/js/**", "/images/**", "/lib/**",
                    "/favicon.ico")
                    .permitAll();
            auth.anyRequest().authenticated();

        });
        http.formLogin(from -> {
            from.loginPage("/authentication/login").permitAll();
            from.defaultSuccessUrl("/profile/dashboard", true);
            from.failureUrl("/authentication/login?error=true");
            from.usernameParameter("email");
            from.passwordParameter("password");
            from.permitAll();
        });

        http.logout(logout -> {
            logout.logoutUrl("/authentication/logout");
            logout.logoutSuccessUrl("/authentication/login");
            logout.invalidateHttpSession(true);
            logout.deleteCookies("JSESSIONID");
            logout.clearAuthentication(true);
            logout.logoutRequestMatcher(new AntPathRequestMatcher("/authentication/logout"));

            logout.permitAll();
        });

        http.authenticationProvider(provider);
        http.sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .maximumSessions(1)
                .maxSessionsPreventsLogin(true)
                .expiredUrl("/login?expired=true")
                .sessionRegistry(sessionRegistry));


        http.oauth2Login(oauth2 -> {
            oauth2.defaultSuccessUrl("/profile/dashboard", true);
            oauth2.loginPage("/login");
            oauth2.failureUrl("/login?error=true");
            oauth2.userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService));
            oauth2.successHandler(oAuth2LoginSuccessHandler);
        });

        http.exceptionHandling(ex -> ex.accessDeniedHandler(((request, response, accessDeniedException) -> {
            response.setStatus(403);
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Type", "text/html");
            response.setContentType("text/html");
            response.sendRedirect("/scm/home");
            response.getWriter().write("Access Denied");

        })));


        return http.build();
    }

    @Bean
    AuthenticationManager authenticationManager(@NotNull AuthenticationConfiguration configuration)
            throws Exception {
        return configuration.getAuthenticationManager();
    }

}

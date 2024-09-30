/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.github.angel.scm.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 *
 * @author aguero
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class HttpSecurityConfiguration {
    private final AuthenticationProvider provider;
    

    public HttpSecurityConfiguration(AuthenticationProvider provider) {
        this.provider = provider;
       
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> {
            auth.requestMatchers("/authentication/**", "/home/**", "/css/**", "/js/**", "/images/**", "/lib/**", "/favicon.ico")
                    .permitAll();
            auth.anyRequest().authenticated();
        });

        http.formLogin(from -> {
            from.loginPage("/authentication/login").permitAll();
           from.defaultSuccessUrl("/", true);
            from.permitAll();
        });

        http.logout(logout -> {
            logout.logoutUrl("/authentication/logout");
            logout.logoutSuccessUrl("/authentication/login");
            logout.invalidateHttpSession(true);
            logout.deleteCookies("JSESSIONID");
            logout.clearAuthentication(true);


            logout.permitAll();
        });

        http.authenticationProvider(provider);
       

        return http.build();
    }

}

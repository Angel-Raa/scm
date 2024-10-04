package com.github.angel.scm.security;

import com.github.angel.scm.persistence.entity.Profile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.*;

/**
 * Custom OAuth2 User implementation that combines OAuth2User information with custom profile data.
 * This class allows you to access both OAuth2User attributes and custom profile data from a single object.
 * @author aguero
 *
 */
public final class CustomOAuth2User implements OAuth2User {
    private OAuth2User oAuth2User;
    private Profile profile;

    public CustomOAuth2User() {
    }

    public CustomOAuth2User(OAuth2User oAuth2User, Profile profile) {
        this.oAuth2User = oAuth2User;
        this.profile = profile;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oAuth2User.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>(oAuth2User.getAuthorities());
        authorities.addAll(profile.getAuthorities()); // Agregar las autoridades personalizadas
        return authorities;
    }

    @Override
    public String getName() {
        return oAuth2User.getAttribute("name");
    }

    public String getEmail() {
        return oAuth2User.getAttribute("email");
    }

    public OAuth2User getoAuth2User() {
        return oAuth2User;
    }

    public void setOAuth2User(OAuth2User oAuth2User) {
        this.oAuth2User = oAuth2User;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}

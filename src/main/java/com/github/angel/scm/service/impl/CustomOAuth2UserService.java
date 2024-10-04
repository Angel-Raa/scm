package com.github.angel.scm.service.impl;

import com.github.angel.scm.persistence.entity.Profile;
import com.github.angel.scm.persistence.entity.Role;
import com.github.angel.scm.persistence.repository.ProfileRepository;
import com.github.angel.scm.persistence.repository.RoleRepository;
import com.github.angel.scm.security.CustomOAuth2User;
import com.github.angel.scm.utils.Authorities;
import com.github.angel.scm.utils.Providers;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Custom OAuth2 User Service that handles user registration and authentication
 * using OAuth2 providers.
 * @author aguero
 *
 */

@Transactional
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final ProfileRepository profileRepository;
    private final RoleRepository repository;

    public CustomOAuth2UserService(ProfileRepository profileRepository, RoleRepository repository) {
        this.profileRepository = profileRepository;
        this.repository = repository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);
        String email = extractEmail(oauth2User, userRequest);
        Profile profile = profileRepository.findByEmail(email)
                .orElseGet(() -> createNewProfile(oauth2User, userRequest));
        return new CustomOAuth2User(oauth2User, profile);
    }

    private Profile createNewProfile(@NotNull OAuth2User oAuth2User, OAuth2UserRequest userRequest){
        Profile profile = new Profile();
        Role defaultRole = new Role();
        defaultRole.setAuthorities(Authorities.USER);
        repository.persist(defaultRole);
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        profile.setEmail(oAuth2User.getAttribute("email"));
        profile.setProviders(getProviders(registrationId)); // Asumimos que el email de Google está verificado
        profile.setProviderUserId(extractProviderId(oAuth2User, registrationId)); // Identificador único de Google
        profile.setEmailVerified(true);
        profile.setEnabled(true);
        profile.setPhoneVerified(false);
        profile.setPhoneNumber("");
        profile.setAbout(""); // Puedes dejarlo vacío o establecer un valor predeterminado
        profile.setProfilePic(extractProfilePic(oAuth2User, registrationId)); // URL de la imagen de perfil de Google
        profile.setRoleId(defaultRole.getRoleId());


        return profileRepository.persist(profile);
    }

    private String extractEmail(OAuth2User oauth2User, OAuth2UserRequest userRequest){
        if ("github".equals(userRequest.getClientRegistration().getRegistrationId())) {
            return oauth2User.getAttribute("email");
        }
        return oauth2User.getAttribute("email");
    }
    private  String extractProfilePic(OAuth2User oAuth2User, String registrationId){
        if("github".equals(registrationId)){
            return oAuth2User.getAttribute("avatar_url");
        }
        return oAuth2User.getAttribute("picture");
    }

    private  Providers getProviders(String registrationId){
        return "github".equals(registrationId) ? Providers.GITHUB : Providers.GOOGLE;
    }

    private  String extractProviderId(OAuth2User oAuth2User, String registrationId){
        if("github".equals(registrationId)){
            return oAuth2User.getAttribute("id").toString();
        }
        return oAuth2User.getAttribute("sub");
    }
}

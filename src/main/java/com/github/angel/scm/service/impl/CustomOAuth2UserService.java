package com.github.angel.scm.service.impl;

import com.github.angel.scm.persistence.entity.Profile;
import com.github.angel.scm.persistence.entity.Role;
import com.github.angel.scm.persistence.repository.ProfileRepository;
import com.github.angel.scm.persistence.repository.RoleRepository;
import com.github.angel.scm.security.CustomOAuth2User;
import com.github.angel.scm.utils.Authorities;
import com.github.angel.scm.utils.Providers;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger log = LoggerFactory.getLogger(CustomOAuth2UserService.class);
    private final ProfileRepository profileRepository;
    private final RoleRepository repository;

    public CustomOAuth2UserService(ProfileRepository profileRepository, RoleRepository repository) {
        this.profileRepository = profileRepository;
        this.repository = repository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        log.info(registrationId);
        String email = extractEmail(oauth2User, registrationId);
        Profile profile = profileRepository.findByEmail(email)
                .orElseGet(() -> createNewProfile(oauth2User, registrationId));
        return new CustomOAuth2User(oauth2User, profile);
    }

    /**
     * Creates a new profile if the user doesn't exist.
     * @param oAuth2User
     * @param userRequest
     * @return
     */
    private Profile createNewProfile(@NotNull OAuth2User oAuth2User, String registrationId){
        Profile profile = new Profile();
        Role defaultRole = new Role();
        defaultRole.setAuthorities(Authorities.USER);
        repository.persist(defaultRole);

        profile.setEmail(oAuth2User.getAttribute("email"));
        profile.setProviders(getProviders(registrationId)); // Asumimos que el email de Google está verificado
        profile.setProviderUserId(extractProviderId(oAuth2User, registrationId)); // Identificador único de Google y github
        profile.setEmailVerified(true);
        profile.setEnabled(true);
        profile.setPhoneVerified(false);
        profile.setPhoneNumber("");
        profile.setAbout("This is the default profile"); // Puedes dejarlo vacío o establecer un valor predeterminado
        profile.setProfilePic(extractProfilePic(oAuth2User, registrationId)); // URL de la imagen de perfil de Google o GitHub
        profile.setRoleId(defaultRole.getRoleId());

        return profileRepository.persist(profile);
    }

    /**
     * Extracts the email from the OAuth2User.
     * @param oauth2User
     * @param registrationId
     * @return
     */
    private String extractEmail(OAuth2User oauth2User, String registrationId){
        if ("github".equals(registrationId)) {
            return oauth2User.getAttribute("email");
        }
        return oauth2User.getAttribute("email");
    }

    /**
     * Extracts the profile picture URL from the OAuth2User.
     * @param oAuth2User
     * @param registrationId
     * @return
     */
    private  String extractProfilePic(OAuth2User oAuth2User, String registrationId){
        if("github".equals(registrationId)){
            return oAuth2User.getAttribute("avatar_url");
        }
        return oAuth2User.getAttribute("picture");
    }

    /**
     * Gets the provider based on the registration ID.
     * @param registrationId
     * @return
     */
    private  Providers getProviders(String registrationId){
        return "github".equals(registrationId) ? Providers.GITHUB : Providers.GOOGLE;
    }

    /**
     * Extracts the provider ID from the OAuth2User.
     * @param oAuth2User
     * @param registrationId
     * @return String
     */
    private  String extractProviderId(OAuth2User oAuth2User, String registrationId){
        if("github".equals(registrationId)){
            return oAuth2User.getAttribute("id");
        }
        return oAuth2User.getAttribute("sub");
    }
}

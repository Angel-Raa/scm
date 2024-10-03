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
        String email = oauth2User.getAttribute("email");
        Profile profile = profileRepository.findByEmail(email)
                .orElseGet(() -> createNewProfile(oauth2User));
        return new CustomOAuth2User(oauth2User, profile);
    }

    private Profile createNewProfile(@NotNull OAuth2User oAuth2User){
        Profile profile = new Profile();
        Role defaultRole = new Role();
        defaultRole.setAuthorities(Authorities.USER);
        repository.persist(defaultRole);
        profile.setEmail(oAuth2User.getAttribute("email"));
        profile.setProviders(Providers.GOOGLE); // Asumimos que el email de Google está verificado
        profile.setProviderUserId(oAuth2User.getAttribute("sub")); // Identificador único de Google
        profile.setEmailVerified(true);
        profile.setEnabled(true);
        profile.setPhoneVerified(false);
        profile.setPhoneNumber("");
        profile.setAbout(""); // Puedes dejarlo vacío o establecer un valor predeterminado
        profile.setProfilePic(oAuth2User.getAttribute("picture")); // URL de la imagen de perfil de Google
        profile.setRoleId(defaultRole.getRoleId());


        return profileRepository.persist(profile);
    }
}

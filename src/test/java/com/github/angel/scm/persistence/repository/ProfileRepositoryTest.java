package com.github.angel.scm.persistence.repository;

import com.github.angel.scm.dto.response.UserDTO;
import com.github.angel.scm.persistence.entity.Profile;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class ProfileRepositoryTest {
    @Autowired
    private ProfileRepository profileRepository;
    @Test
    void existsByEmail_shouldReturnTrue_whenEmailExists() {
        // given
        String email = "test@example.com";
        Profile profile = new Profile();
        profile.setEmail(email);
        profileRepository.persist(profile);

        // when
        boolean exists = profileRepository.existsByEmail(email);

        // then
        assertTrue(exists);
    }

    @Test
    void existsByEmail_shouldReturnFalse_whenEmailDoesNotExist() {
        // given
        String email = "test@example.com";

        // when
        boolean exists = profileRepository.existsByEmail(email);

        // then
        assertFalse(exists);
    }

    @Test
    void findByEmail_shouldReturnProfile_whenEmailExists() {
        // given
        String email = "test@example.com";
        Profile profile = new Profile();
        profile.setEmail(email);
        profileRepository.persist(profile);

        // when
        Optional<Profile> optionalProfile = profileRepository.findByEmail(email);

        // then
        assertTrue(optionalProfile.isPresent());
        assertEquals(email, optionalProfile.get().getEmail());
    }
    @Test
    void findByEmail_shouldReturnEmptyOptional_whenEmailDoesNotExist() {
        // given
        String email = "test@example.com";

        // when
        Optional<Profile> optionalProfile = profileRepository.findByEmail(email);

        // then
        assertTrue(optionalProfile.isEmpty());
    }

    @Test
    void findByEmailDto_shouldReturnUserDto_whenEmailExists() {
        // given
        String email = "test@example.com";
        Profile profile = new Profile();
        profile.setEmail(email);
        profileRepository.persist(profile);

        // when
        Optional<UserDTO> optionalUserDto = profileRepository.findByEmailDto(email);

        // then
        assertTrue(optionalUserDto.isPresent());
        assertEquals(email, optionalUserDto.get().getEmail());
    }

    @Test
    void findByEmailDto_shouldReturnEmptyOptional_whenEmailDoesNotExist() {
        // given
        String email = "test@example.com";

        // when
        Optional<UserDTO> optionalUserDto = profileRepository.findByEmailDto(email);

        // then
        assertTrue(optionalUserDto.isEmpty());
    }

    @Test
    void findByUserIdDto_shouldReturnUserDto_whenUserIdExists() {
        // given
        UUID userId = UUID.randomUUID();
        Profile profile = new Profile();
        profile.setUserId(userId);
        profileRepository.persist(profile);

        // when
        Optional<UserDTO> optionalUserDto = profileRepository.findByUserIdDto(userId);

        // then
        assertTrue(optionalUserDto.isPresent());
        assertEquals(userId, optionalUserDto.get().getUserdId());
    }

    @Test
    void findByUserIdDto_shouldReturnEmptyOptional_whenUserIdDoesNotExist() {
        // given
        UUID userId = UUID.randomUUID();

        // when
        Optional<UserDTO> optionalUserDto = profileRepository.findByUserIdDto(userId);

        // then
        assertTrue(optionalUserDto.isEmpty());
    }

    @Test
    void updateEmail_shouldUpdateEmail_whenEmailExists() {
        // given
        String oldEmail = "test@example.com";
        String newEmail = "newtest@example.com";
        Profile profile = new Profile();
        profile.setEmail(oldEmail);
        profileRepository.persist(profile);

        // when
        int updatedRows = profileRepository.updateEmail(oldEmail, newEmail);

        // then
        assertEquals(1, updatedRows);
        assertTrue(profileRepository.existsByEmail(newEmail));
        assertFalse(profileRepository.existsByEmail(oldEmail));
    }

    @Test
    void updateEmail_shouldNotUpdateEmail_whenEmailDoesNotExist() {
        // given
        String oldEmail = "test@example.com";
        String newEmail = "newtest@example.com";

        // when
        int updatedRows = profileRepository.updateEmail(oldEmail, newEmail);

        // then
        assertEquals(0, updatedRows);
        assertFalse(profileRepository.existsByEmail(newEmail));
    }

    @Test
    void findAll_shouldReturnAllUsers() {
        // given
        Profile profile1 = new Profile();
        profile1.setEmail("test1@example.com");
        profileRepository.persist(profile1);

        Profile profile2 = new Profile();
        profile2.setEmail("test2@example.com");
        profileRepository.persist(profile2);

        // when
        Page<UserDTO> userDtoPage = profileRepository.findAll(PageRequest.of(0, 10));

        // then
        assertEquals(2, userDtoPage.getTotalElements());
    }
}
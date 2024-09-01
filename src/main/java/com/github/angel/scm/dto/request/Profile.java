/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.github.angel.scm.dto.request;

import java.io.Serial;
import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 *
 * @author aguero
 */
public class Profile implements Serializable {
    @Serial
    private static final Long serialVersionUID = -329784086378491232L;
    @Size(max = 150, message = "About me must be less than 150 characters")
    private String about;
    private String profilePic;
    @NotBlank(message = "phone is required")
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be 10 digits long")
    private String phoneNumber;

    public Profile() {
    }

    public Profile(@Size(max = 150, message = "About me must be less than 150 characters") String about,
            String profilePic,
            @NotBlank(message = "phone is required") @Pattern(regexp = "^\\d{10}$", message = "Phone number must be 10 digits long") String phoneNumber) {
        this.about = about;
        this.profilePic = profilePic;
        this.phoneNumber = phoneNumber;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}

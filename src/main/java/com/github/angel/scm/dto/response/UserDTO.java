/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.github.angel.scm.dto.response;

import java.io.Serializable;
import java.util.UUID;

/**
 *
 * @author aguero
 */
public class UserDTO implements Serializable{
    private static final long serialVersionUID = -38729342718673288L;
    private UUID userdId;
    private String email;
    private String about;
    private String profilePic;
    private String phoneNumber;
    
    public UserDTO() {
    }
    public UserDTO(UUID userdId, String email, String about, String profilePic, String phoneNumber) {
        this.userdId = userdId;
        this.email = email;
        this.about = about;
        this.profilePic = profilePic;
        this.phoneNumber = phoneNumber;
    }
    public UUID getUserdId() {
        return userdId;
    }
    public void setUserdId(UUID userdId) {
        this.userdId = userdId;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
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
    @Override
    public String toString() {
        return "UserDTO [userdId=" + userdId + ", email=" + email + ", about=" + about + ", profilePic=" + profilePic
                + ", phoneNumber=" + phoneNumber + "]";
    }
    


}

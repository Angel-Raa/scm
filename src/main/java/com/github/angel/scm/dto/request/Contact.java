/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.github.angel.scm.dto.request;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 *
 * @author aguero
 */
public class Contact implements Serializable {
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email address")
    private String email;
    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be 10 digits long")
    private String phoneNumber;
    private String address;
    private String description;
    private String favorite;
    private String websiteLink;
    private String linkedInLink;
    private MultipartFile contactImage;
    private String picture;

    
    public Contact() {
    }
    public Contact(@NotBlank(message = "Name is required") String name,
            @NotBlank(message = "Email is required") @Email(message = "Invalid email address") String email,
            @NotBlank(message = "Phone number is required") @Pattern(regexp = "^\\d{10}$", message = "Phone number must be 10 digits long") String phoneNumber,
            String address, String description, String favorite, String websiteLink, String linkedInLink,
            MultipartFile contactImage, String picture) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.description = description;
        this.favorite = favorite;
        this.websiteLink = websiteLink;
        this.linkedInLink = linkedInLink;
        this.contactImage = contactImage;
        this.picture = picture;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getFavorite() {
        return favorite;
    }
    public void setFavorite(String favorite) {
        this.favorite = favorite;
    }
    public String getWebsiteLink() {
        return websiteLink;
    }
    public void setWebsiteLink(String websiteLink) {
        this.websiteLink = websiteLink;
    }
    public String getLinkedInLink() {
        return linkedInLink;
    }
    public void setLinkedInLink(String linkedInLink) {
        this.linkedInLink = linkedInLink;
    }
    public MultipartFile getContactImage() {
        return contactImage;
    }
    public void setContactImage(MultipartFile contactImage) {
        this.contactImage = contactImage;
    }
    public String getPicture() {
        return picture;
    }
    public void setPicture(String picture) {
        this.picture = picture;
    }
    @Override
    public String toString() {
        return "Contact [name=" + name + ", email=" + email + ", phoneNumber=" + phoneNumber + ", address=" + address
                + ", description=" + description + ", favorite=" + favorite + ", websiteLink=" + websiteLink
                + ", linkedInLink=" + linkedInLink + ", contactImage=" + contactImage + ", picture=" + picture + "]";
    }

}

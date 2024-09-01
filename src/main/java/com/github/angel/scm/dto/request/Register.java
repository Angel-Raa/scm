/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.github.angel.scm.dto.request;

import java.io.Serial;
import java.io.Serializable;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 *
 * @author aguero
 */
public class Register implements Serializable {
    @Serial
    private static final Long serialVersionUID = -1927162515291726182L;
    @Email(message = "Invalid email address")
    @NotBlank(message = "Email is required")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Invalid email format")
    private String email;
    @NotBlank(message = "Password is required")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$", message = "Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, and one digit")
    private String password;
    @NotBlank(message = "phone is required")
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be 10 digits long")
    private String phoneNumber;
    @Size(max = 150, message = "About me must be less than 150 characters")
    private String about;

    public Register() {
    }

    public Register(
            @Email(message = "Invalid email address") @NotBlank(message = "Email is required") @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Invalid email format") String email,
            @NotBlank(message = "Password is required") @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$", message = "Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, and one digit") String password,
            @NotBlank(message = "phone is required") @Pattern(regexp = "^\\d{10}$", message = "Phone number must be 10 digits long") String phoneNumber,
            @Size(max = 150, message = "About me must be less than 150 characters") String about) {
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.about = about;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    @Override
    public String toString() {
        return "Register [email=" + email + ", password=" + password + ", phoneNumber=" + phoneNumber + ", about="
                + about + "]";
    }

}

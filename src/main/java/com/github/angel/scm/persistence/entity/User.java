/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.github.angel.scm.persistence.entity;


import java.util.*;
import java.sql.Types;
import java.util.stream.Collectors;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.github.angel.scm.utils.Providers;

import io.hypersistence.utils.hibernate.type.array.ListArrayType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author aguero
 */
@Entity(name = "User")
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = { "email" }))
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "user_id")
    private UUID userId;
    @Column(name = "fk_role_id", insertable = true, updatable = true)
    private UUID roleId;
    @JdbcTypeCode(Types.VARCHAR)
    private String email;
    @JdbcTypeCode(Types.VARCHAR)
    private String password;
    @JdbcTypeCode(Types.VARCHAR)
    @Lob
    private String about;
    @Column(name = "profile_pic")
    @JdbcTypeCode(Types.VARCHAR)
    private String profilePic;
    @Column(name = "phone_number", length = 20)
    private String phoneNumber;
    @JdbcTypeCode(Types.BOOLEAN)
    private boolean enabled = true;
    @Column(name = "email_verified")
    @JdbcTypeCode(Types.BOOLEAN)
    private boolean emailVerified = false;
    @Column(name = "phone_verified")
    @JdbcTypeCode(Types.BOOLEAN)
    private boolean phoneVerified = false;
    @Enumerated(EnumType.STRING)
    private Providers providers = Providers.SELF;
    @Column(name = "provider_user_id")
    private String providerUserId;
    @JdbcTypeCode(Types.VARCHAR)
    @Column(name = "email_token")
    private String emailToken;
    @ManyToOne(cascade = CascadeType.ALL, targetEntity = Role.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_role_id", insertable = false, updatable = false, referencedColumnName = "role_id")
    private Role role;

    @Type(value = ListArrayType.class, parameters = {
            @Parameter(name = ListArrayType.SQL_ARRAY_TYPE, value = "contact")
    })
    @JsonManagedReference
    @Column(name = "contact", columnDefinition = "contacts[]")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = Contact.class, orphanRemoval = true, mappedBy = "user")
    private List<Contact> contact = new ArrayList<>();

    public User() {
    }

    public User(UUID userId, UUID roleId, String email, String password, String about, String profilePic,
            String phoneNumber, boolean enabled, boolean emailVerified, boolean phoneVerified, Providers providers,
            String providerUserId, String emailToken, Role role, List<Contact> contact) {
        this.userId = userId;
        this.roleId = roleId;
        this.email = email;
        this.password = password;
        this.about = about;
        this.profilePic = profilePic;
        this.phoneNumber = phoneNumber;
        this.enabled = enabled;
        this.emailVerified = emailVerified;
        this.phoneVerified = phoneVerified;
        this.providers = providers;
        this.providerUserId = providerUserId;
        this.emailToken = emailToken;
        this.role = role;
        this.contact = contact;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getRoleId() {
        return roleId;
    }

    public void setRoleId(UUID roleId) {
        this.roleId = roleId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(role == null) return Collections.emptyList();
        if(role.getAuthorities() == null) return Collections.emptyList();
        return role.getAuthorities()
                .getPermissions()
                .stream().map(Enum::name)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    public void setPassword(String password) {
        this.password = password;
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

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public boolean isPhoneVerified() {
        return phoneVerified;
    }

    public void setPhoneVerified(boolean phoneVerified) {
        this.phoneVerified = phoneVerified;
    }

    public Providers getProviders() {
        return providers;
    }

    public void setProviders(Providers providers) {
        this.providers = providers;
    }

    public String getProviderUserId() {
        return providerUserId;
    }

    public void setProviderUserId(String providerUserId) {
        this.providerUserId = providerUserId;
    }

    public String getEmailToken() {
        return emailToken;
    }

    public void setEmailToken(String emailToken) {
        this.emailToken = emailToken;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Contact> getContact() {
        return contact;
    }

    public void setContact(List<Contact> contact) {
        this.contact = contact;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((userId == null) ? 0 : userId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        if (userId == null) {
            if (other.userId != null)
                return false;
        } else if (!userId.equals(other.userId))
            return false;
        return true;
    }

}

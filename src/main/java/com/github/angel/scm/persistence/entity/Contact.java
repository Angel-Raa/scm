/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.github.angel.scm.persistence.entity;

import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.hypersistence.utils.hibernate.type.array.ListArrayType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
import java.util.List;
import java.util.ArrayList;
import java.io.Serial;
import java.io.Serializable;

/**
 *
 * @author aguero
 */
@Table(name = "contacts", uniqueConstraints = @UniqueConstraint(columnNames = { "email" }))
@Entity(name = "Contact")
public class Contact implements Serializable {
    @Serial
    private static final Long serialVersionUID = -8828329332224123242L;
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "contact_id")
    private UUID contactId;
    @Column(name = "fk_user_id", insertable = true, updatable = true)
    private UUID userId;
    @Column(name = "name", length = 50, nullable = false)
    private String name;
    @Column(name = "email", length = 50, nullable = false)
    private String email;
    @Column(name = "phone_number", length = 20)
    private String phoneNumber;
    @Column(name = "address", length = 70)
    private String address;
    @Column(name = "picture", length = 100)
    private String picture;
    @Lob
    @JdbcTypeCode(java.sql.Types.LONGVARCHAR)
    private String description;
    @JdbcTypeCode(java.sql.Types.BOOLEAN)
    private boolean favorite = false;
    @Column(name = "website_link", length = 100)
    private String websiteLink;
    @Column(name = "linkedin_link", length = 100)
    private String linkedInLink;
    @Column(name = "cloudinary_image_public_id", length = 100)
    private String cloudinaryImagePublicId;
    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "fk_user_id", insertable = false, updatable = false, referencedColumnName = "user_id")
    @JsonBackReference
    private User user;

    @Type(value = ListArrayType.class, parameters = {
            @Parameter(name = ListArrayType.SQL_ARRAY_TYPE, value = "social_links")
    })
    @JsonManagedReference
    @Column(name = "social_links", columnDefinition = "social_links[]")
    @OneToMany(mappedBy = "contact", cascade = jakarta.persistence.CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<SocialLink> socialLinks = new ArrayList<>();

    public Contact() {
    }

    public Contact(UUID contactId, UUID userId, String name, String email, String phoneNumber, String address,
            String picture, String description, boolean favorite, String websiteLink, String linkedInLink,
            String cloudinaryImagePublicId, User user, List<SocialLink> socialLinks) {
        this.contactId = contactId;
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.picture = picture;
        this.description = description;
        this.favorite = favorite;
        this.websiteLink = websiteLink;
        this.linkedInLink = linkedInLink;
        this.cloudinaryImagePublicId = cloudinaryImagePublicId;
        this.user = user;
        this.socialLinks = socialLinks;
    }

    public UUID getContactId() {
        return contactId;
    }

    public void setContactId(UUID contactId) {
        this.contactId = contactId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
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

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
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

    public String getCloudinaryImagePublicId() {
        return cloudinaryImagePublicId;
    }

    public void setCloudinaryImagePublicId(String cloudinaryImagePublicId) {
        this.cloudinaryImagePublicId = cloudinaryImagePublicId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<SocialLink> getSocialLinks() {
        return socialLinks;
    }

    public void setSocialLinks(List<SocialLink> socialLinks) {
        this.socialLinks = socialLinks;
    }

    @Override
    public String toString() {
        return "Contact [contactId=" + contactId + ", userId=" + userId + ", name=" + name + ", email=" + email
                + ", phoneNumber=" + phoneNumber + ", address=" + address + ", picture=" + picture + ", description="
                + description + ", favorite=" + favorite + ", websiteLink=" + websiteLink + ", linkedInLink="
                + linkedInLink + ", cloudinaryImagePublicId=" + cloudinaryImagePublicId + ", user=" + user + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((contactId == null) ? 0 : contactId.hashCode());
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
        Contact other = (Contact) obj;
        if (contactId == null) {
            if (other.contactId != null)
                return false;
        } else if (!contactId.equals(other.contactId))
            return false;
        return true;
    }

}

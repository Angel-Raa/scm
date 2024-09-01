/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.github.angel.scm.persistence.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;

/**
 *
 * @author aguero
 */
@Table(name = "social_links")
@Entity(name = "SocialLink")
public class SocialLink implements Serializable {
    @Serial
    private static final Long serialVersionUID = -1828329332224123242L;
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "social_link_id")
    private UUID socialLinkId;
    @Column(name = "fk_contact_id", insertable = true, updatable = true)
    private UUID contactId;
    @Column(name = "link", length = 150)
    private String link;
    @Column(name = "title", length = 50)
    private String title;
    @JsonBackReference
    @ManyToOne(targetEntity = Contact.class)
    @JoinColumn(name = "fk_contact_id", insertable = false, updatable = false, referencedColumnName = "contact_id")
    private Contact contact;

    public SocialLink() {}

    public SocialLink(UUID socialLinkId, UUID contactId, String link, String title, Contact contact) {
        this.socialLinkId = socialLinkId;
        this.contactId = contactId;
        this.link = link;
        this.title = title;
        this.contact = contact;
    }


    public UUID getSocialLinkId() {
        return socialLinkId;
    }

    public void setSocialLinkId(UUID socialLinkId) {
        this.socialLinkId = socialLinkId;
    }

    public UUID getContactId() {
        return contactId;
    }

    public void setContactId(UUID contactId) {
        this.contactId = contactId;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    @Override
    public String toString() {
        return "SocialLink [socialLinkId=" + socialLinkId + ", contactId=" + contactId + ", link=" + link + ", title="
                + title + ", contact=" + contact + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((socialLinkId == null) ? 0 : socialLinkId.hashCode());
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
        SocialLink other = (SocialLink) obj;
        if (socialLinkId == null) {
            if (other.socialLinkId != null)
                return false;
        } else if (!socialLinkId.equals(other.socialLinkId))
            return false;
        return true;
    }

}

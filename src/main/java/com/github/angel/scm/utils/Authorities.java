/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */

package com.github.angel.scm.utils;

import java.util.List;

/**
 *
 * Enum representing the different authorities or roles in the system.
 * Each authority has a set of associated permissions.
 * @author aguero
 * 
 */
public enum Authorities {
    USER(
            List.of(Permissions.READ, Permissions.CREATE, Permissions.UPDATE, Permissions.DISABLE)),
    ADMIN(
            List.of(Permissions.READ, Permissions.CREATE, Permissions.UPDATE, Permissions.DISABLE, Permissions.DELETE));

    private final List<Permissions> permissions;

    private Authorities(List<Permissions> permissions) {
        this.permissions = permissions;
    }

    public List<Permissions> getPermissions() {
        return permissions;
    }

}

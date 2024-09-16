/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.github.angel.scm.dto.request;

import java.io.Serial;
import java.io.Serializable;

/**
 *
 * @author aguero
 */
public class ContactSearchForm implements Serializable{
    @Serial
    private static final long serialVersionUID = -1928278120987319328L;
    private String field;
    private String value;
    public ContactSearchForm() {
    }
    public ContactSearchForm(String field, String value) {
        this.field = field;
        this.value = value;
    }
    public String getField() {
        return field;
    }
    public void setField(String field) {
        this.field = field;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    @Override
    public String toString() {
        return "ContactSearchForm [field=" + field + ", value=" + value + "]";
    }



}

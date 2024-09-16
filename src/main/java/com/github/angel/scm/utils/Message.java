/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.github.angel.scm.utils;

/**
 *
 * @author aguero
 */
public class Message {
    private String content;
    private MessageType type = MessageType.BLUE;
    public Message() {
    }
    public Message(String content, MessageType type) {
        this.content = content;
        this.type = type;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public MessageType getType() {
        return type;
    }
    public void setType(MessageType type) {
        this.type = type;
    }
    @Override
    public String toString() {
        return "Message [content=" + content + ", type=" + type + "]";
    }

    

}

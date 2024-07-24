package com.example.awaysuse.model;

import lombok.*;

import java.io.Serializable;


public class Help implements Serializable {

   int userId;

    public Help setUserId(int uid) {
        this.userId = uid;
        return this;
    }

    public int getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "Help{" +
            "userId=" + userId +
            '}';
    }
}

package com.example.awaysuse.entity;

import java.util.HashSet;
import java.util.Objects;

public class User {

    private String name;
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    //  重写hashcode和equals方法
    @Override
    public boolean equals(Object obj) {
        User user = (User) obj;
        return user.getName().equals(this.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }



}

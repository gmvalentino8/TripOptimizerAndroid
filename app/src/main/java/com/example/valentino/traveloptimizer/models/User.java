package com.example.valentino.traveloptimizer.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Valentino on 3/27/18.
 */

public class User {
    @SerializedName("email")
    private String email;
    @SerializedName("name")
    private String name;
    @SerializedName("birthDate")
    private String birthdate;
    @SerializedName("address")
    private String address;
    @SerializedName("age")
    private String age;

    public User(String email, String name, String birthdate, String address) {
        this.email = email;
        this.name = name;
        this.birthdate = birthdate;
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}

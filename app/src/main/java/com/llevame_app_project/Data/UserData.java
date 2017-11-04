package com.llevame_app_project.Data;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserData {

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String email;

    public void setName(String nname) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    @SerializedName("firstName")
    @Expose
    private String name;

    @SerializedName("firstName")
    @Expose
    private String lastName;

    @SerializedName("creditCardNumber")
    @Expose
    private String creditCardNumber;

    public String getMusic() {
        return music;
    }

    public void setMusic(String music) {
        this.music = music;
    }

    @SerializedName("music")
    @Expose
    private String music;
}

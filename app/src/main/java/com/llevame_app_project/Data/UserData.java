package com.llevame_app_project.Data;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserData {

    public String getNname() {
        return nname;
    }

    public void setNname(String nname) {
        this.nname = nname;
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
    private String nname;

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

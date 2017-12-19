package com.llevame_app_project.Data.UserData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.llevame_app_project.Data.UserData.LocationData.LocationData;

public class UserData {


    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("firstName")
    @Expose
    private String firstName;

    @SerializedName("lastName")
    @Expose
    private String lastName;

    @SerializedName("creditCardNumber")
    @Expose
    private String creditCardNumber;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("isDriver")
    @Expose
    private boolean isDriver;

    @SerializedName("location")
    @Expose
    private LocationData location;

    @SerializedName("balance")
    @Expose
    private BalanceData balance;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
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

    public boolean isDriver() {
        return isDriver;
    }

    public void setDriver(boolean driver) {
        isDriver = driver;
    }

    public LocationData getLocation() {
        return location;
    }

    public void setLocation(LocationData location) {
        this.location = location;
    }

    public BalanceData getBalance() {
        return balance;
    }

    public void setBalance(BalanceData balance) {
        this.balance = balance;
    }
}

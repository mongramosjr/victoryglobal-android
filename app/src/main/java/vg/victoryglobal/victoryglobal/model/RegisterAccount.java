/*
 * Created by Mong Ramos Jr. <mongramosjr@gmail.com> on 9/14/17 7:41 PM
 *
 * Copyright (c) 2017 Victory Global Unlimited Systems Inc. All rights reserved.
 *
 * Last modified 9/13/17 7:51 PM
 */

package vg.victoryglobal.victoryglobal.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.provider.ContactsContract;

import java.sql.Date;

public class RegisterAccount implements Parcelable {

    private String firstName = "";
    private String lastName = "";
    private String activationCode = "";

    private int sponsorId = 0;
    private int uplineId = 0;
    private String password = "";
    private String verifyPassword = "";

    private String middleName;

    private Date dateOfBirth;
    private String maritalStatus;
    private String gender;
    private String taxNumber;
    private String socialSecurityNumber;

    private String street;
    private String city;
    private String region;
    private String postalCode;
    private String countryCode;
    private String email;
    private String telephone;
    private String mobileNumber;

    private int mlmAccountId;
    private int mlmLocation;
    private int pickupCenterId;

    private String activationCodeName = "";
    private String sponsorName = "";
    private String uplineName = "";
    private String mlmAccountName = "";


    public RegisterAccount(){

    }

    public RegisterAccount(String firstName, String lastName, String activationCode, int sponsorId, int uplineId, String password){
        this.firstName = firstName;
        this.lastName = lastName;
        this.activationCode = activationCode;
        this.sponsorId = sponsorId;
        this.uplineId = uplineId;
        this.password = password;
    }

    protected RegisterAccount(Parcel in) {
        firstName = in.readString();
        lastName = in.readString();
        activationCode = in.readString();
        sponsorId = in.readInt();
        uplineId = in.readInt();
        password = in.readString();
    }

    public static final Creator<RegisterAccount> CREATOR = new Creator<RegisterAccount>() {
        @Override
        public RegisterAccount createFromParcel(Parcel in) {
            return new RegisterAccount(in);
        }

        @Override
        public RegisterAccount[] newArray(int size) {
            return new RegisterAccount[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(firstName);
        parcel.writeString(lastName);
        parcel.writeString(activationCode);
        parcel.writeInt(sponsorId);
        parcel.writeInt(uplineId);
        parcel.writeString(password);
    }



    //setter and getter
    public String getFirstName(){return firstName; }
    public void setFirstName(String firstName) {this.firstName = firstName; }

    public String getLastName(){return lastName; }
    public void setLastName(String lastName) {this.lastName = lastName; }

    public String getActivationCode(){return activationCode; }
    public void setActivationCode(String activationCode) {this.activationCode = activationCode; }

    public int getSponsorId(){ return sponsorId; }
    public void setSponsorId(int sponsorId) {this.sponsorId = sponsorId; }

    public int getUplineId(){ return uplineId; }
    public void setUplineId(int uplineId) {this.uplineId = uplineId; }

    public String getPassword() { return password; }
    public void setPassword(String password) {this.password = password; }


    public String getMiddleName() { return middleName; }
    public void setMiddleName(String middleName) {this.middleName = middleName; }

    public Date getDateOfBirth () { return dateOfBirth; }
    public void setDateOfBirth(Date dateOfBirth) {this.dateOfBirth = dateOfBirth; }


    public String getMaritalStatus() { return maritalStatus; }
    public void setMaritalStatus(String maritalStatus) {this.maritalStatus = maritalStatus; }


    public String getGender() { return gender; }
    public void setGender(String gender) {this.gender = gender; }


    public String getTaxNumber() { return taxNumber; }
    public void setTaxNumber(String taxNumber) {this.taxNumber = taxNumber; }


    public String getSocialSecurityNumber() { return socialSecurityNumber; }
    public void setSocialSecurityNumber(String socialSecurityNumber) {this.socialSecurityNumber = socialSecurityNumber; }


    public String getStreet() { return street; }
    public void setStreet(String street) {this.street = street; }


    public String getCity() { return city; }
    public void setCity(String city) {this.city = city; }


    public String getRegion() { return region; }
    public void setRegion(String region) {this.region = region; }


    public String getPostalCode() { return postalCode; }
    public void setPostalCode(String postalCode) {this.postalCode = postalCode; }


    public String getCountryCode() { return countryCode; }
    public void setCountryCode(String countryCode) {this.countryCode = countryCode; }



    public String getEmail() { return email; }
    public void setEmail(String email) {this.email = email; }


    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) {this.telephone = telephone; }


    public String getMobileNumber() { return mobileNumber; }
    public void setMobileNumber(String mobileNumber) {this.mobileNumber = mobileNumber; }


    public int getMlmAccountId() { return mlmAccountId; }
    public void setMlmAccountId(int mlmAccountId) {this.mlmAccountId = mlmAccountId; }


    public int getMlmLocation() { return mlmLocation; }
    public void setMlmLocation(int mlmLocation) {this.mlmLocation = mlmLocation; }


    public int getPickupCenterId() { return pickupCenterId; }
    public void setPickupCenterId(int pickupCenterId) {this.pickupCenterId = pickupCenterId; }

    public String getActivationCodeName() { return activationCodeName; }
    public void setActivationCodeName(String activationCodeName) { this.activationCodeName = activationCodeName; }

    public String getSponsorName() { return sponsorName; }
    public void setSponsorName(String sponsorName) { this.sponsorName = sponsorName; }

    public String getUplineName() { return uplineName; }
    public void setUplineName(String uplineName) { this.uplineName = uplineName; }

    public String getVerifyPassword() { return verifyPassword; }
    public void setVerifyPassword(String verifyPassword) { this.verifyPassword = verifyPassword; }

    public String getMlmAccountName() { return mlmAccountName; }

    public void setMlmAccountName(String mlmAccountName) { this.mlmAccountName = mlmAccountName; }
}

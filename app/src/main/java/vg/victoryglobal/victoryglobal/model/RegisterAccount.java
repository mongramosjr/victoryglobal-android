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

import java.sql.Date;

public class RegisterAccount implements Parcelable {

    private String firstName;
    private String lastName;
    private String activationCode;

    private Integer sponsorId;
    private Integer uplineId;
    private String password;
    private String verifyPassword;

    private String middleName;

    private Date dateOfBirth;
    private String maritalStatus;
    private Integer gender;
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

    private Integer mlmAccountId;
    private Integer mlmLocation;
    private Integer pickupCenterId;

    private String activationCodeName;
    private String sponsorName;
    private String uplineName;
    private String mlmAccountName;

    private String pickupCenterName;
    private String mlmLocationName;

    private String genderName;



    public RegisterAccount(){

    }

    public RegisterAccount(String firstName, String lastName, String activationCode, Integer sponsorId, Integer uplineId, String password){
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
        if (in.readByte() == 0) {
            sponsorId = null;
        } else {
            sponsorId = in.readInt();
        }
        if (in.readByte() == 0) {
            uplineId = null;
        } else {
            uplineId = in.readInt();
        }
        password = in.readString();
        verifyPassword = in.readString();
        middleName = in.readString();
        maritalStatus = in.readString();
        if (in.readByte() == 0) {
            gender = null;
        } else {
            gender = in.readInt();
        }
        taxNumber = in.readString();
        socialSecurityNumber = in.readString();
        street = in.readString();
        city = in.readString();
        region = in.readString();
        postalCode = in.readString();
        countryCode = in.readString();
        email = in.readString();
        telephone = in.readString();
        mobileNumber = in.readString();
        if (in.readByte() == 0) {
            mlmAccountId = null;
        } else {
            mlmAccountId = in.readInt();
        }
        if (in.readByte() == 0) {
            mlmLocation = null;
        } else {
            mlmLocation = in.readInt();
        }
        if (in.readByte() == 0) {
            pickupCenterId = null;
        } else {
            pickupCenterId = in.readInt();
        }
        activationCodeName = in.readString();
        sponsorName = in.readString();
        uplineName = in.readString();
        mlmAccountName = in.readString();
        pickupCenterName = in.readString();
        mlmLocationName = in.readString();
        genderName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(activationCode);
        if (sponsorId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(sponsorId);
        }
        if (uplineId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(uplineId);
        }
        dest.writeString(password);
        dest.writeString(verifyPassword);
        dest.writeString(middleName);
        dest.writeString(maritalStatus);
        if (gender == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(gender);
        }
        dest.writeString(taxNumber);
        dest.writeString(socialSecurityNumber);
        dest.writeString(street);
        dest.writeString(city);
        dest.writeString(region);
        dest.writeString(postalCode);
        dest.writeString(countryCode);
        dest.writeString(email);
        dest.writeString(telephone);
        dest.writeString(mobileNumber);
        if (mlmAccountId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(mlmAccountId);
        }
        if (mlmLocation == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(mlmLocation);
        }
        if (pickupCenterId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(pickupCenterId);
        }
        dest.writeString(activationCodeName);
        dest.writeString(sponsorName);
        dest.writeString(uplineName);
        dest.writeString(mlmAccountName);
        dest.writeString(pickupCenterName);
        dest.writeString(mlmLocationName);
        dest.writeString(genderName);
    }

    @Override
    public int describeContents() {
        return 0;
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

    //setter and getter
    public String getFirstName(){return firstName; }
    public void setFirstName(String firstName) {this.firstName = firstName; }

    public String getLastName(){return lastName; }
    public void setLastName(String lastName) {this.lastName = lastName; }

    public String getActivationCode(){return activationCode; }
    public void setActivationCode(String activationCode) {this.activationCode = activationCode; }

    public Integer getSponsorId(){ return sponsorId; }
    public void setSponsorId(Integer sponsorId) {this.sponsorId = sponsorId; }

    public Integer getUplineId(){ return uplineId; }
    public void setUplineId(Integer uplineId) {this.uplineId = uplineId; }

    public String getPassword() { return password; }
    public void setPassword(String password) {this.password = password; }


    public String getMiddleName() { return middleName; }
    public void setMiddleName(String middleName) {this.middleName = middleName; }

    public Date getDateOfBirth () { return dateOfBirth; }
    public void setDateOfBirth(Date dateOfBirth) {this.dateOfBirth = dateOfBirth; }


    public String getMaritalStatus() { return maritalStatus; }
    public void setMaritalStatus(String maritalStatus) {this.maritalStatus = maritalStatus; }


    public Integer getGender() { return gender; }
    public void setGender(Integer gender) {this.gender = gender; }


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


    public Integer getMlmAccountId() {
        return mlmAccountId;
    }

    public void setMlmAccountId(Integer mlmAccountId) {
        this.mlmAccountId = mlmAccountId;
    }

    public Integer getMlmLocation() { return mlmLocation; }
    public void setMlmLocation(Integer mlmLocation) {this.mlmLocation = mlmLocation; }



    public Integer getPickupCenterId() { return pickupCenterId; }
    public void setPickupCenterId(Integer pickupCenterId) {this.pickupCenterId = pickupCenterId; }

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

    public String getMlmLocationName() {
        return mlmLocationName;
    }

    public void setMlmLocationName(String mlmLocationName) {
        this.mlmLocationName = mlmLocationName;
    }

    public String getPickupCenterName() {
        return pickupCenterName;
    }

    public void setPickupCenterName(String pickupCenterName) {
        this.pickupCenterName = pickupCenterName;
    }

    public String getGenderName() {
        return genderName;
    }

    public void setGenderName(String genderName) {
        this.genderName = genderName;
    }
}

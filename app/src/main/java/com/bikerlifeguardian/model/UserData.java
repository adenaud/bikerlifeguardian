package com.bikerlifeguardian.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;

import java.sql.Date;

public class UserData implements Parcelable {

    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private int gender;
    @DatabaseField
    private String firstName;
    @DatabaseField
    private String lastName;
    @DatabaseField
    private Date birthDate;
    @DatabaseField
    private String bloodGroup;
    @DatabaseField
    private String phone;
    @DatabaseField
    private String langages;
    @DatabaseField
    private String allergies;
    @DatabaseField
    private String medicines;
    @DatabaseField
    private String healthIssues;
    @DatabaseField
    private String repLegalFirstname;
    @DatabaseField
    private String repLegalLastname;
    @DatabaseField
    private String repLegalPhone;
    @DatabaseField
    private String coments;

    public UserData(){

    }

    protected UserData(Parcel in) {
        id = in.readInt();
        gender = in.readInt();
        firstName = in.readString();
        lastName = in.readString();
        bloodGroup = in.readString();
        phone = in.readString();
        birthDate = (Date) in.readSerializable();
        langages = in.readString();
        allergies = in.readString();
        medicines = in.readString();
        healthIssues = in.readString();
        repLegalFirstname = in.readString();
        repLegalLastname = in.readString();
        repLegalPhone = in.readString();
        coments = in.readString();
    }

    public static final Creator<UserData> CREATOR = new Creator<UserData>() {
        @Override
        public UserData createFromParcel(Parcel in) {
            return new UserData(in);
        }

        @Override
        public UserData[] newArray(int size) {
            return new UserData[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLangages() {
        return langages;
    }

    public void setLangages(String langages) {
        this.langages = langages;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public String getMedicines() {
        return medicines;
    }

    public void setMedicines(String medicines) {
        this.medicines = medicines;
    }

    public String getHealthIssues() {
        return healthIssues;
    }

    public void setHealthIssues(String healthIssues) {
        this.healthIssues = healthIssues;
    }

    public String getRepLegalFirstname() {
        return repLegalFirstname;
    }

    public void setRepLegalFirstname(String repLegalFirstname) {
        this.repLegalFirstname = repLegalFirstname;
    }

    public String getRepLegalLastname() {
        return repLegalLastname;
    }

    public void setRepLegalLastname(String repLegalLastname) {
        this.repLegalLastname = repLegalLastname;
    }

    public String getComents() {
        return coments;
    }

    public void setComents(String coments) {
        this.coments = coments;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getRepLegalPhone() {
        return repLegalPhone;
    }

    public void setRepLegalPhone(String repLegalPhone) {
        this.repLegalPhone = repLegalPhone;
    }

    @Override
    public int describeContents() {
        return id;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(gender);
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(bloodGroup);
        dest.writeString(phone);
        dest.writeSerializable(birthDate);
        dest.writeString(langages);
        dest.writeString(allergies);
        dest.writeString(medicines);
        dest.writeString(healthIssues);
        dest.writeString(repLegalFirstname);
        dest.writeString(repLegalLastname);
        dest.writeString(repLegalPhone);
        dest.writeString(coments);
    }
}

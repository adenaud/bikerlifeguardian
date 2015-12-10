package com.bikerlifeguardian.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;

import java.sql.Date;

public class UserData implements Parcelable {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private String uuid;

    @DatabaseField
    private int gender;
    @DatabaseField
    private String firstname;
    @DatabaseField
    private String lastname;
    @DatabaseField
    private Date birthDate;
    @DatabaseField
    private String bloodGroup;
    @DatabaseField
    private String phone;
    @DatabaseField
    private String languages;
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
    private String comments;
    @DatabaseField
    private String email;
    @DatabaseField
    private String password;

    public UserData(){

    }

    protected UserData(Parcel in) {
        id = in.readInt();
        gender = in.readInt();
        firstname = in.readString();
        lastname = in.readString();
        bloodGroup = in.readString();
        phone = in.readString();
        birthDate = (Date) in.readSerializable();
        languages = in.readString();
        allergies = in.readString();
        medicines = in.readString();
        healthIssues = in.readString();
        repLegalFirstname = in.readString();
        repLegalLastname = in.readString();
        repLegalPhone = in.readString();
        comments = in.readString();
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

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
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

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int describeContents() {
        return id;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(gender);
        dest.writeString(firstname);
        dest.writeString(lastname);
        dest.writeString(bloodGroup);
        dest.writeString(phone);
        dest.writeSerializable(birthDate);
        dest.writeString(languages);
        dest.writeString(allergies);
        dest.writeString(medicines);
        dest.writeString(healthIssues);
        dest.writeString(repLegalFirstname);
        dest.writeString(repLegalLastname);
        dest.writeString(repLegalPhone);
        dest.writeString(comments);
    }
}

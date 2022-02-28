package com.bwt.mypawnshop;

import java.io.Serializable;
import java.sql.Date;

public class CustomerInfo implements Serializable {
    public String cus_name;
    public String cus_mob_1;
    public String cus_mob_2;
    public String cus_gender;
    public String cus_dob;
    public String cus_marital_status;
    public String cus_address;
    public String cus_aadhar_front;
    public String cus_aadhar_back;
    public String cus_pan_front;
    public String cus_pan_back;
    public String cus_other_proof1;
    public String cus_other_proof2;
    public Date cus_created_at;
    public int user_id;

    public String getCus_name() {
        return cus_name;
    }

    public void setCus_name(String cus_name) {
        this.cus_name = cus_name;
    }

    public String getCus_mob_1() {
        return cus_mob_1;
    }

    public void setCus_mob_1(String cus_mob_1) {
        this.cus_mob_1 = cus_mob_1;
    }

    public String getCus_mob_2() {
        return cus_mob_2;
    }

    public void setCus_mob_2(String cus_mob_2) {
        this.cus_mob_2 = cus_mob_2;
    }

    public String getCus_gender() {
        return cus_gender;
    }

    public void setCus_gender(String cus_gender) {
        this.cus_gender = cus_gender;
    }

    public String getCus_dob() {
        return cus_dob;
    }

    public void setCus_dob(String cus_dob) {
        this.cus_dob = cus_dob;
    }

    public String getCus_marital_status() {
        return cus_marital_status;
    }

    public void setCus_marital_status(String cus_marital_status) {
        this.cus_marital_status = cus_marital_status;
    }

    public String getCus_address() {
        return cus_address;
    }

    public void setCus_address(String cus_address) {
        this.cus_address = cus_address;
    }

    public String getCus_aadhar_front() {
        return cus_aadhar_front;
    }

    public void setCus_aadhar_front(String cus_aadhar_front) {
        this.cus_aadhar_front = cus_aadhar_front;
    }

    public String getCus_aadhar_back() {
        return cus_aadhar_back;
    }

    public void setCus_aadhar_back(String cus_aadhar_back) {
        this.cus_aadhar_back = cus_aadhar_back;
    }

    public String getCus_pan_front() {
        return cus_pan_front;
    }

    public void setCus_pan_front(String cus_pan_front) {
        this.cus_pan_front = cus_pan_front;
    }

    public String getCus_pan_back() {
        return cus_pan_back;
    }

    public void setCus_pan_back(String cus_pan_back) {
        this.cus_pan_back = cus_pan_back;
    }

    public String getCus_other_proof1() {
        return cus_other_proof1;
    }

    public void setCus_other_proof1(String cus_other_proof1) {
        this.cus_other_proof1 = cus_other_proof1;
    }

    public String getCus_other_proof2() {
        return cus_other_proof2;
    }

    public void setCus_other_proof2(String cus_other_proof2) {
        this.cus_other_proof2 = cus_other_proof2;
    }

    public Date getCus_created_at() {
        return cus_created_at;
    }

    public void setCus_created_at(Date cus_created_at) {
        this.cus_created_at = cus_created_at;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "CustomerInfo{" +
                "cus_name='" + cus_name + '\'' +
                ", cus_mob_1='" + cus_mob_1 + '\'' +
                ", cus_mob_2='" + cus_mob_2 + '\'' +
                ", cus_gender='" + cus_gender + '\'' +
                ", cus_dob='" + cus_dob + '\'' +
                ", cus_marital_status='" + cus_marital_status + '\'' +
                ", cus_address='" + cus_address + '\'' +
                ", cus_aadhar_front='" + cus_aadhar_front + '\'' +
                ", cus_aadhar_back='" + cus_aadhar_back + '\'' +
                ", cus_pan_front='" + cus_pan_front + '\'' +
                ", cus_pan_back='" + cus_pan_back + '\'' +
                ", cus_other_proof1='" + cus_other_proof1 + '\'' +
                ", cus_other_proof2='" + cus_other_proof2 + '\'' +
                ", cus_created_at='" + cus_created_at + '\'' +
                ", user_id=" + user_id +
                '}';
    }
}

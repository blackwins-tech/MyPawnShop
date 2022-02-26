package com.bwt.mypawnshop;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;

public class ShopOwnerInfo implements Serializable {
    public int user_id;
    public String user_app_lang;
    public String user_mob_no;
    public String user_mpin;
    public String user_name;
    public String otp_verification;
    public Date user_created_at;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_app_lang() {
        return user_app_lang;
    }

    public void setUser_app_lang(String user_app_lang) {
        this.user_app_lang = user_app_lang;
    }

    public String getUser_mob_no() {
        return user_mob_no;
    }

    public void setUser_mob_no(String user_mob_no) {
        this.user_mob_no = user_mob_no;
    }

    public String getUser_mpin() {
        return user_mpin;
    }

    public void setUser_mpin(String user_mpin) {
        this.user_mpin = user_mpin;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public Date getUser_created_at() {
        return user_created_at;
    }

    public void setUser_created_at(Date user_created_at) {
        this.user_created_at = user_created_at;
    }

    public String getOtp_verification() {
        return otp_verification;
    }

    public void setOtp_verification(String otp_verification) {
        this.otp_verification = otp_verification;
    }

    @Override
    public String toString() {
        return "ShopOwnerInfo{" +
                "user_id=" + user_id +
                ", user_app_lang='" + user_app_lang + '\'' +
                ", user_mob_no='" + user_mob_no + '\'' +
                ", user_mpin='" + user_mpin + '\'' +
                ", user_name='" + user_name + '\'' +
                ", otp_verification='" + otp_verification + '\'' +
                ", user_created_at=" + user_created_at +
                '}';
    }

}

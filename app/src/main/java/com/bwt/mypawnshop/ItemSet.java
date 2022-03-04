package com.bwt.mypawnshop;

import java.sql.Date;

public class ItemSet {

    public Date set_num_created_at;
    public int user_id;

    public Date getSet_num_created_at() {
        return set_num_created_at;
    }

    public void setSet_num_created_at(Date set_num_created_at) {
        this.set_num_created_at = set_num_created_at;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "ItemSet{" +
                "set_num_created_at='" + set_num_created_at + '\'' +
                ", user_id=" + user_id +
                '}';
    }
}


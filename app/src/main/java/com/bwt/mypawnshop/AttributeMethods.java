package com.bwt.mypawnshop;

import com.google.gson.JsonObject;

import java.sql.Date;
import java.time.LocalDate;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AttributeMethods {

    /*@FormUrlEncoded
    @POST("api/user")
    Call<ShopOwnerInfo> postShopOwnerInfo(@Field("user_app_lang") String user_app_lang,
                                            @Field("user_mob_no") String user_mob_no,
                                            @Field("user_mpin") String user_mpin,
                                            @Field("user_name")String user_name,
                                            @Field("user_created_at") Date user_created_at,
                                            @Field("otp_verification")String otp_verification);*/

    @POST("api/user")
    Call<ShopOwnerInfo> postShopOwnerInfo(@Body JsonObject shopOwnerInfo);

    @POST("api/customer")
    Call<CustomerInfo> postCustomerInfo(@Body JsonObject shopOwnerInfo);

    @POST("api/itemsets")
    Call<ItemSet> postItemSetsInfo(@Body JsonObject itemSets);
}

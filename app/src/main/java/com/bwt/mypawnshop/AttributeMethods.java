package com.bwt.mypawnshop;

import java.sql.Date;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AttributeMethods {

    @FormUrlEncoded
    @POST("api/user")
    Call<ShopOwnerInfo> postShopOwnerInfo(@Field ("user_app_lang") String user_app_lang,
                                            @Field("user_mob_no") String user_mob_no,
                                            @Field("user_mpin") String user_mpin,
                                            @Field("user_name")String user_name,
                                            @Field("user_created_at") String user_created_at);
}

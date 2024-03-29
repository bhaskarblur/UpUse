package com.classified.upuse.APIWork;

import com.classified.upuse.Models.AdsModel;
import com.classified.upuse.Models.AuthResponse;
import com.classified.upuse.Models.chatModel;
import com.classified.upuse.Models.homeResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiWork {

    @FormUrlEncoded
    @POST("login")
    Call<AuthResponse.SendOtp> sendotp(@Field("mobile") String mobile);

    @FormUrlEncoded
    @POST("otp")
    Call<AuthResponse.VerifyOtp> login(@Field("mobile") String mobile,
    @Field("otp") String otp,@Field("device_token") String token);

    @GET("all-state")
    Call<AuthResponse.getstate> getstate();

    @GET("state-by-city")
    Call<AuthResponse.getcity> getcity(@Query("state") String state);

    @FormUrlEncoded
    @POST("profile_update")
    Call<AuthResponse.profile_update> withoutimage_updateprofile(@Field("user_id") String userid,
                                                    @Field("name") String name,
                                                    @Field("state") String state,
                                                    @Field("city") String city,
                                                                 @Field("mobile")String mobile);

    @FormUrlEncoded
    @POST("profile_update")
    Call<AuthResponse.profile_update> updateprofile(@Field("user_id") String userid,
                                                    @Field("name") String name,
                                                    @Field("state") String state,
                                                    @Field("city") String city,
                                                    @Field("image") String image);

    @FormUrlEncoded
    @POST("profile_update")
    Call<AuthResponse.profile_update> updateprofile2(@Field("user_id") String userid,
                                                    @Field("name") String name,
                                                    @Field("state") String state,
                                                    @Field("city") String city,
                                                    @Field("image") String image,
                                                     @Field("mobile")String mobile);


    @FormUrlEncoded
    @POST("get_user")
    Call<AuthResponse.VerifyOtp> getprofile(@Field("user_id") String userid);

    @GET("banner")
    Call<homeResponse.bannerResp> getBanners();

    @GET("category")
    Call<homeResponse.categoryResp> getCategories();


    @FormUrlEncoded
    @POST("product")
    Call<homeResponse.ListadsResp> getAds(@Field("city") String city,
                                          @Field("user_id") String userid);

    @GET("city")
    Call<homeResponse.listofcities> getallcities(@Query("q") String query,
                                                 @Query("page") Integer limit);



    @FormUrlEncoded
    @POST("favorite")
    Call<homeResponse.ListadsResp> getfavAds(@Field("user_id") String userid);


    @FormUrlEncoded
    @POST("my_product")
    Call<homeResponse.ListadsResp> get_myads(@Field("user_id") String userid);

    @FormUrlEncoded
    @POST("delete_account")
    Call<AuthResponse.SendOtp> delete_account(@Field("user_id") String userid);

    @FormUrlEncoded
    @POST("remove_favorite")
    Call<AuthResponse.SendOtp> remove_favourite(@Field("product_id") String ad_id,
                                                @Field("user_id") String userid);


    @FormUrlEncoded
    @POST("add_to_favorite")
    Call<AuthResponse.SendOtp> add_favourite(@Field("product_id") String ad_id,
                                                @Field("user_id") String userid);

    @FormUrlEncoded
    @POST("single_product")
    Call<AdsModel.adsResp> single_product(@Field("product_id") String ad_id,
                                           @Field("user_id") String userid);

    @FormUrlEncoded
    @POST("add_product")
    Call<AdsModel.postadsResp> post_automobile( @Field("user_id") String userid,
                                                @Field("product_name") String name,
                                                @Field("product_title") String title,
                                                @Field("product_type") String product_type,
                                                @Field("product_description")String descr,
                                                @Field("location") String city,
                                                @Field("product_sale_price") String sellprice,
                                                @Field("product_image") String image,
                                                @Field("brand") String brand,
                                                @Field("model") String model,
                                                @Field("purchase_date") String date,
                                                @Field("fuel_type") String fueltype,
                                                @Field("transmission") String transm,
                                                @Field("number_of_owners") String numown,
                                                @Field("kmdriven") String kmdriven,
                                                @Field("show_number") String number,
                                                @Field("product_category_name") String catname,
                                                @Field("latitude") String lat,
                                                @Field("longitude") String longit);

    @FormUrlEncoded
    @POST("add_product")
    Call<AdsModel.postadsResp> post_property( @Field("user_id") String userid,
                                                @Field("product_name") String name,
                                                @Field("product_title") String title,
                                                @Field("product_type") String product_type,
                                                @Field("product_description")String descr,
                                                @Field("location") String city,
                                                @Field("product_sale_price") String sellprice,
                                                @Field("product_image") String image,
                                                @Field("property_type") String prop_type,
                                                @Field("ad_type") String ad_type,
                                                @Field("area") String area,
                                                @Field("land_type") String land_type,
                                              @Field("show_number") String number,
                                              @Field("product_category_name") String catname,
                                              @Field("latitude") String lat,
                                              @Field("longitude") String longit);

    @FormUrlEncoded
    @POST("add_product")
    Call<AdsModel.postadsResp> post_other( @Field("user_id") String userid,
                                              @Field("product_name") String name,
                                              @Field("product_title") String title,
                                              @Field("product_type") String product_type,
                                              @Field("product_description")String descr,
                                              @Field("product_price") String origprice,
                                              @Field("location") String city,
                                              @Field("product_sale_price") String sellprice,
                                              @Field("product_image") String image,
                                              @Field("condition") String condition,
                                              @Field("in_warranty") String warranty,
                                              @Field("brand") String brand,
                                              @Field("purchase_date") String date,
                                           @Field("show_number") String number,
                                           @Field("product_category_name") String catname,
                                           @Field("latitude") String lat,
                                           @Field("longitude") String longit);

    @FormUrlEncoded
    @POST("remove_product")
    Call<AuthResponse.SendOtp> delete_post(@Field("product_id") String ad_id,
                                           @Field("user_id") String userid,
                                           @Field("reason") String reason);

    @FormUrlEncoded
    @POST("views")
    Call<AuthResponse.SendOtp> put_views(@Field("product_id") String ad_id,
                                           @Field("user_id") String userid);

    @FormUrlEncoded
    @POST("notification")
    Call<homeResponse.notiResp> get_notifications(@Field("user_id") String userid);

    @FormUrlEncoded
    @POST("promotion")
    Call<AuthResponse.SendOtp> do_promotion(@Field("product_id") String ad_id,
                                         @Field("user_id") String userid,
                                            @Field("start_date") String start_date,
                                            @Field("end_date") String end_date,
                                            @Field("location") String city,
                                            @Field("promotion_cost") String cost,
                                            @Field("promotion_reach") String reach);


    @FormUrlEncoded
    @POST("reach")
    Call<homeResponse.reachresult> get_reach(@Field("city") String city);

    @FormUrlEncoded
    @POST("recommended")
    Call<homeResponse.ListadsResp> get_recommendedads(@Field("product_id") String prodid,
                                                      @Field("user_id") String userid);

    @FormUrlEncoded
    @POST("chats")
    Call<chatModel.listChats> get_allchats(@Field("user_id") String userid);

    @FormUrlEncoded
    @POST("single_chat")
    Call<chatModel.insidechatResp> get_singlechats(@Field("user_id") String userid,
                                              @Field("product_id") String productid,
                                              @Field("person_id") String personid);

    @FormUrlEncoded
    @POST("delete_chats")
    Call<AuthResponse.SendOtp> delete_singlechat(@Field("user_id") String userid,
                                                   @Field("product_id") String productid,
                                                   @Field("person_id") String personid);

    @FormUrlEncoded
    @POST("block")
    Call<AuthResponse.SendOtp> block(@Field("user_id") String userid,
                                                     @Field("product_id") String productid,
                                                     @Field("person_id") String personid);

    @FormUrlEncoded
    @POST("search")
    Call<homeResponse.ListadsResp> search_ads(@Field("user_id") String userid,
                                              @Field("keyword") String word,
                                              @Field("category_name") String catname,
                                              @Field("price_start") String start_price,
                                              @Field("price_end")String end_price,
                                              @Field("price_sort") String sortby,
                                              @Field("date_start") String datestart,
                                              @Field("date_end") String enddate,
                                              @Field("city") String city);

    @FormUrlEncoded
    @POST("search")
    Call<homeResponse.ListadsResp> search_ads2(@Field("user_id") String userid,
                                               @Field("keyword") String word);
}

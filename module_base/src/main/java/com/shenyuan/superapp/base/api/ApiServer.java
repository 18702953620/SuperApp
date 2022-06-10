package com.shenyuan.superapp.base.api;


import java.util.HashMap;

import io.reactivex.Flowable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @author ch
 * @date 2020/4/21-10:20
 * desc ApiServer
 */
public interface ApiServer {
    /**
     * 刷新token
     *
     * @param map map
     * @return Call
     */
    @FormUrlEncoded
    @POST("auth/oauth/token")
    Call<HashMap<String, String>> refreshToken(@FieldMap HashMap<String, String> map);

    /**
     * 刷新token
     *
     * @param name map
     * @return Call
     */
    @GET("https://www.mxnzp.com/api/news/types")
    Flowable<HashMap<String, String>> login(@Query("name") String name, @Query("pwd") String pwd);
}

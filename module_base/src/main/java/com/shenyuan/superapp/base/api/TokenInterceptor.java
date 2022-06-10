package com.shenyuan.superapp.base.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.shenyuan.superapp.base.api.common.AppConstant;
import com.shenyuan.superapp.base.api.common.TokenCommon;
import com.shenyuan.superapp.base.api.fastjson.FastJsonConverterFactory;

import java.io.IOException;
import java.net.Proxy;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okio.BufferedSource;
import okio.Okio;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * @author ch
 * @date 2020/12/21-9:42
 * desc
 */
public class TokenInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);

        if (response.body() != null) {
            BufferedSource buffer = Okio.buffer(response.body().source());
            String jsonString = buffer.readUtf8();
            JSONObject object = JSON.parseObject(jsonString);
            String code = object.getString("code");
            if ("A0230".equals(code)) {
                //需要刷新token
                OkHttpClient client = new OkHttpClient.Builder()
                        .addInterceptor(new LogInterceptor())
                        //禁用代理
                        .proxy(Proxy.NO_PROXY)
                        .connectTimeout(10, TimeUnit.SECONDS)
                        .readTimeout(10, TimeUnit.SECONDS)
                        .build();

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(AppConstant.BASE_SERVER_URL)
                        //添加自定义的解析器
                        //支持RxJava2
                        .addConverterFactory(FastJsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .client(client)
                        .build();

                ApiServer apiServer = retrofit.create(ApiServer.class);

                HashMap<String, String> map = new HashMap<>();
                map.put("grant_type", "refresh_token");
                map.put("client_id", AppConstant.CLIENT_ID);
                map.put("client_secret", AppConstant.CLIENT_SECRET);
                map.put("user_type", AppConstant.USER_TYPE);
                map.put("refresh_token", TokenCommon.getRefreshToken());
                //同步请求
                retrofit2.Response<HashMap<String, String>> tokenResponse = apiServer.refreshToken(map).execute();
                if (tokenResponse.body() != null) {
                    //保存token
                    TokenCommon.saveToken(tokenResponse.body().get("token"));
                    TokenCommon.saveRefreshToken(tokenResponse.body().get("refreshToken"));

                    //添加token
                    Request newRequest = request.newBuilder()
                            .addHeader("Authorization", "Bearer " + TokenCommon.getToken())
                            .build();
                    response.close();
                    try {
                        return chain.proceed(newRequest);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return response;
    }
}
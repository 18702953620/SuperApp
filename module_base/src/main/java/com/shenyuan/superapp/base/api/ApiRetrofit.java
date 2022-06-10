package com.shenyuan.superapp.base.api;

import androidx.annotation.NonNull;

import com.shenyuan.superapp.base.api.common.AppConstant;
import com.shenyuan.superapp.base.api.fastjson.FastJsonConverterFactory;

import java.net.Proxy;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * @author ch
 * 时间： 2016/12/27.13:56
 * 描述：ApiRetrofit
 * 来源：
 */
public class ApiRetrofit {
    private volatile static ApiRetrofit apiRetrofit;
    private final ApiServer apiServer;
    private final Retrofit retrofit;

    private ApiRetrofit() {
        //添加log拦截器
        OkHttpClient client = new OkHttpClient.Builder()
                //公共参数 链接替换
                .addInterceptor(new BasicParamsInterceptor.Builder()
                        .build())

                //添加log拦截器
                .addInterceptor(new LogInterceptor())
                //token 刷新
//                .addInterceptor(new TokenInterceptor())
                //禁用代理
                .proxy(Proxy.NO_PROXY)
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl(AppConstant.BASE_SERVER_URL)
                //添加自定义的解析器
                .addConverterFactory(FastJsonConverterFactory.create())
                //支持RxJava2
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();

        apiServer = retrofit.create(ApiServer.class);
    }

    public static ApiRetrofit getInstance() {
        if (apiRetrofit == null) {
            synchronized (ApiRetrofit.class) {
                if (apiRetrofit == null) {
                    apiRetrofit = new ApiRetrofit();
                }
            }
        }
        return apiRetrofit;
    }

    public ApiServer getApiService() {
        return apiServer;
    }

    /**
     * 获取 ApiService
     *
     * @param serviceCls serviceCls
     * @param <T>        T
     * @return T
     */
    public <T> T getService(@NonNull final Class<T> serviceCls) {
        return retrofit.create(serviceCls);
    }
}

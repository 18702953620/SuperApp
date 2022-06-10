package com.shenyuan.superapp.base.api;

import android.util.Log;

import com.shenyuan.superapp.base.utils.AppUtils;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @author ch
 * @date 2020/12/21-14:02
 * desc
 */
public class LogInterceptor implements Interceptor {
    private static final String TAG = "ApiRetrofit";

    @NotNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        long startTime = System.currentTimeMillis();
        Response response = chain.proceed(chain.request());
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        MediaType mediaType = null;
        String content = "";
        if (response.body() != null) {
            mediaType = response.body().contentType();
            content = response.body().string();
            Log.e(TAG, "----------Request Start----------------");
            Log.e(TAG, "| " + request.toString() + request.headers().toString());
            Log.e(TAG, "| Response:" + AppUtils.unicodeToutf8(content));
            Log.e(TAG, "----------Request End:" + duration + "毫秒----------");
        }

        return response.newBuilder()
                .body(ResponseBody.create(mediaType, content))
                .build();
    }
}

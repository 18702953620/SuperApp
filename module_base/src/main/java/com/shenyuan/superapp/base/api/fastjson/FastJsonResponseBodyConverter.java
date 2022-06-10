package com.shenyuan.superapp.base.api.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.shenyuan.superapp.base.api.exception.TokenInvalidException;
import com.shenyuan.superapp.base.api.exception.UserAccountReplacedException;
import com.shenyuan.superapp.base.api.common.TokenCommon;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import okio.BufferedSource;
import okio.Okio;
import retrofit2.Converter;

/**
 * @author ch
 * @date 2020/1/6 17:48
 * desc FastJsonResponseBodyConverter
 */
public class FastJsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    private final Type type;

    FastJsonResponseBodyConverter(Type type) {
        this.type = type;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        BufferedSource buffer = Okio.buffer(value.source());
        String jsonString = buffer.readUtf8();
        try {
            JSONObject object = JSON.parseObject(jsonString);
            String code = object.getString("code");
            String msg = object.getString("msg");
            if ("1".equals(code)) {
                Object data = object.get("data");

                if (type instanceof Class) {
                    Class cls = (Class) type;
                    //如果是String 则返回msg
                    if ("String".equals(cls.getSimpleName())) {
                        return (T) msg;
                    }
                }

                if (data instanceof String) {
                    return (T) data;
                }
                return JSON.parseObject(object.getString("data"), type, Feature.SupportNonPublicField);
            } else if ("A0232".equals(code)) {
                //token 过期 需要刷新

                //清除token
                TokenCommon.clearToken();

                throw new TokenInvalidException(msg);
            } else if ("A0200".equals(code) || "A0231".equals(code) || "A0233".equals(code) || "A0234".equals(code)) {
                //清除token
                TokenCommon.clearToken();
                //需要重新登录
                throw new TokenInvalidException(msg);
            } else if ("A0204".equals(code)) {
                //清除token
                TokenCommon.clearToken();
                //其他终端登录，需要重新登录
                throw new UserAccountReplacedException(msg);
            } else {
                throw new RuntimeException(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            value.close();
            buffer.close();
        }
    }
}

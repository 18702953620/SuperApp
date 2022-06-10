package com.shenyuan.superapp.base.api;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;

/**
 * @author ch
 * @date 2021/5/6 10:45
 * desc
 */
public class JsonRequestBody extends RequestBody {
    private final String content;

    public JsonRequestBody(String content) {
        this.content = content;
    }


    public JsonRequestBody(Object map) {
        this.content = JSON.toJSONString(map);
    }

    @Override
    public MediaType contentType() {
        return MediaType.parse("application/json; charset=utf-8");
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        Charset charset = StandardCharsets.UTF_8;
        byte[] bytes = content.getBytes(charset);
        sink.write(bytes, 0, bytes.length);
    }
}

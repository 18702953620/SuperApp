package com.shenyuan.superapp.base.api.fastjson;


import com.alibaba.fastjson.JSONWriter;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import retrofit2.Converter;

/**
 * @author ch
 * @date 2020/1/6 17:48
 * @desc FastJsonRequestBodyConverter
 */
public final class FastJsonRequestBodyConverter<T> implements Converter<T, RequestBody> {
    private final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");
    private final Charset UTF_8 = StandardCharsets.UTF_8;


    @Override
    public RequestBody convert(T value) throws IOException {
        Buffer buffer = new Buffer();
        Writer writer = new OutputStreamWriter(buffer.outputStream(), UTF_8);
        JSONWriter jsonWriter = new JSONWriter(writer);
        jsonWriter.writeValue(value);
        jsonWriter.close();
        return RequestBody.create(MEDIA_TYPE, buffer.readByteString());
    }
}

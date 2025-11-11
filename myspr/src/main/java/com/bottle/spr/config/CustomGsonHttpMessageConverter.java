package com.bottle.spr.config;

import com.bottle.util.GsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@Slf4j
public class CustomGsonHttpMessageConverter extends AbstractHttpMessageConverter<Object> {

    public CustomGsonHttpMessageConverter() {
        super(MediaType.APPLICATION_JSON, MediaType.TEXT_HTML);
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        // 指定支持的所有类型，或者返回true表示支持所有类型
        return true;
    }

    @Override
    protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        String body = new String(inputMessage.getBody().readAllBytes(), StandardCharsets.UTF_8);
        return Objects.requireNonNull(GsonUtil.jsonToJavaBean(body, clazz));
    }

    @Override
    protected void writeInternal(Object o, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        String jsonBody ;
        if (o instanceof String){
            jsonBody = String.valueOf(o);
        }else {
            jsonBody = GsonUtil.javaBeanToJson(o);
        }

        assert jsonBody != null;
        outputMessage.getBody().write(jsonBody.getBytes(StandardCharsets.UTF_8));
    }


}

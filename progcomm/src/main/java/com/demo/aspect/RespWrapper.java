package com.demo.aspect;

import com.bottle.util.GoogleGsonUtil;
import com.bottle.util.StringUtil;
import com.demo.beans.RespResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.smile.MappingJackson2SmileHttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;



@ControllerAdvice
@Slf4j
public class RespWrapper implements ResponseBodyAdvice<Object> {


    @Autowired
    private MappingJackson2HttpMessageConverter converter;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // 这里可以指定哪些方法需要被处理，返回true表示对所有带有@ResponseBody的返回值都生效
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        log.info("beforeBodyWrite {} ", body);

        // 统一包装返回值
        if (body instanceof RespResult){
            return body;
        }
        RespResult resp = new RespResult();
        resp.success(body);

        if (body instanceof String){
            try {
                return converter.getObjectMapper().writeValueAsString(resp);
            } catch (JsonProcessingException e) {
                return GoogleGsonUtil.javaBeanToJson(resp);
            }
        }else {
            return resp;
        }

//        return GoogleGsonUtil.javaBeanToJson(resp);
    }

    // 处理异常
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<RespResult> handleBusinessException(Throwable e) {
        log.error("handleBusinessException",e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                e instanceof IllegalStateException || e instanceof IllegalArgumentException ?
                        new RespResult().fail(e.getMessage()) :
                new RespResult().error(StringUtil.printExceptInfo(e))
        )
        ;
    }


}

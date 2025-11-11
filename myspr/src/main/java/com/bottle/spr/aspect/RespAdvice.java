package com.bottle.spr.aspect;

import com.bottle.moduls.OutResultStruct;
import com.bottle.util.GsonUtil;
import com.bottle.util.StringUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;




@Slf4j
@ControllerAdvice
public class RespAdvice implements ResponseBodyAdvice<Object> {


    @Autowired
    private MappingJackson2HttpMessageConverter converter;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // 这里可以指定哪些方法需要被处理，返回true表示对所有带有@ResponseBody的返回值都生效
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        log.info("RespAdvice beforeBodyWrite returnType={} body= {} ",  returnType, body);


        if (body instanceof byte[]) {
            return body;
        }

        // Swagger 直接返回原始配置信息
        String requestPath = request.getURI().getPath();
        if (requestPath.contains("/swagger-ui/") || requestPath.contains("/v3/api-docs") || requestPath.contains("openapiJson")) {
            return body; // 不包装 Swagger 相关接口的返回值
        }

        // 统一包装返回值
        if (body instanceof OutResultStruct){
            return body;
        }

        OutResultStruct resp = new OutResultStruct();
        resp.success(body);

        if (body instanceof String){
            try {
                return converter.getObjectMapper().writeValueAsString(resp);
            } catch (JsonProcessingException e) {
                return GsonUtil.javaBeanToJson(resp);
            }
        }else {
            return resp;
        }

    }

    // 处理异常
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<OutResultStruct> handleBusinessException(Throwable e) {
        log.error("RespAdvice handleBusinessException",e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                e instanceof IllegalStateException || e instanceof IllegalArgumentException ?
                        new OutResultStruct().fail(e.getMessage()) :
                        e instanceof NullPointerException ? new OutResultStruct().fail("NullPointerException") :
                        new OutResultStruct().error(StringUtil.printExceptInfo(e))
        );
    }


}

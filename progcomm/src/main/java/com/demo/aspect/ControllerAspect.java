package com.demo.aspect;

import com.bottle.util.StringUtil;
import com.demo.beans.RespResult;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.util.Arrays;

//@Aspect
//@Component
@Slf4j
public class ControllerAspect {

    @Autowired
    private RequestMappingHandlerAdapter handlerAdapter;

    @Around("execution(* com.demo.controller..*.*(..))")
    public Object aroundController(ProceedingJoinPoint joinPoint) throws Throwable {
        RespResult resp = new RespResult();

        try {
            Object result = joinPoint.proceed();
            log.info("aroundController 方法: {} 参数:{} \n输出: {}",
                    joinPoint.getSignature().getName(),
                    Arrays.toString(joinPoint.getArgs()),
                    result);

            if (result instanceof RespResult){
                resp = (RespResult) result;
            }else{
                resp.success(result);
            }

            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            if (signature.getReturnType() == String.class) {
                return result; // 不包装String返回值
            }

        } catch (Exception e) {
            // 异常处理
            log.error("捕获错误",e);
            resp.error(StringUtil.printExceptInfo(e));
        }
        return resp;

    }



}

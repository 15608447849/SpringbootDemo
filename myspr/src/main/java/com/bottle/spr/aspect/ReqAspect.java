package com.bottle.spr.aspect;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.util.Arrays;
import java.util.Enumeration;


@Slf4j
@Aspect
@Component
public class ReqAspect {

    @Autowired
    private RequestMappingHandlerAdapter handlerAdapter;

    // execution(返回值类型 包名.类名.方法名(参数)) execution(* com.demo.controller..*.*(..))
    @Around("(@within(org.springframework.web.bind.annotation.RestController) || @within(org.springframework.stereotype.Controller)) " +
            "&& (@annotation(org.springframework.web.bind.annotation.RequestMapping) " +
            "|| @annotation(org.springframework.web.bind.annotation.GetMapping) " +
            "|| @annotation(org.springframework.web.bind.annotation.PostMapping) " +
            "|| @annotation(org.springframework.web.bind.annotation.PutMapping) " +
            "|| @annotation(org.springframework.web.bind.annotation.DeleteMapping))"
    )
    public Object aroundController(ProceedingJoinPoint joinPoint) throws Throwable {

        Object result = null;
        try {
            ServletRequestAttributes attributes =
                    (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

            HttpServletRequest request = attributes.getRequest();
            // 请求头
            StringBuffer header_sb = new StringBuffer();
            Enumeration<String> headerNames = request.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                String headerValue = request.getHeader(headerName);
                header_sb.append(headerName).append(" = ").append(headerValue);
                header_sb.append("; ");
            }
            // IP地址优先从nginx代理中获取
            String ip = request.getHeader("X-Forwarded-For");
            if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
            //
            String userAgent = request.getHeader("User-Agent");
            // 访问路径
            String path = request.getRequestURI();
            // cookie
            StringBuffer cookie_sb = new StringBuffer();
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    cookie_sb.append(cookie.getName()).append("=").append(cookie.getValue());
                    cookie_sb.append("; ");
                }
            }

            // 真实方法
            String methodName = joinPoint.getSignature().getName();
            // 请求参数
            String params = Arrays.toString(joinPoint.getArgs());
            log.info("aroundController ip: {} ,path: {} \nheader: {} \ncookie: {}",ip,path,header_sb,cookie_sb);
            log.info("aroundController method: {} ,req: {} ",methodName,params);
            result = joinPoint.proceed();
            log.info("aroundController method: {} ,resp: {}", methodName, result);

        } catch (Exception e) {
            // 异常处理
            log.error("捕获错误",e);
        }
        return result;

    }

}

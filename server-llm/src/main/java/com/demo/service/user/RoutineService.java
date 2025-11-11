package com.demo.service.user;

import com.bottle.jdbc.GenUniqueID;
import com.bottle.jdbc.JDBC;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.startup.Tomcat;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class RoutineService {

    public boolean checkRegister(String uniqueVar){
        log.info("检查用户是否注册 唯一值={}", uniqueVar);

        List<Object[]> check =
                JDBC.getFacadeFirst().query(
                        "SELECT 0 FROM t_user where cstatus&1=0 and wx_openid=? OR phone=?",
                        new Object[]{uniqueVar, uniqueVar},
                        null);

        return !check.isEmpty();
    }

    public boolean register(String uniqueVar,String phone,String email,String nickname,String imgUrl){
        log.info("用户尝试注册 唯一值={} , 手机号码={} , 电子邮箱={} 昵称={} , 头像={}",
                uniqueVar,phone,email,nickname,imgUrl);
        long oid = GenUniqueID.milliSecondID.currentTimestampLong();

        int execute =
                JDBC.getFacadeFirst().
                        execute("insert into t_user (oid,phone,wx_openid,wx_nickname) values (?,?,?,?)",
                                new Object[]{oid, phone, uniqueVar, nickname});

        return execute>0;
    }

}

package com.demo.service.wchat;

import com.bottle.util.GoogleGsonUtil;
import com.demo.config.WXConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@Slf4j
@Service
public class WXMinProgService {



    // 访问用户openid
    private static final String URL_TEMPLATE =
            "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";

    @Autowired
    private WXConfig config;


    public Map<String,String> getOpenid(String code) {
        String url = String.format(URL_TEMPLATE, config.getMinprog().getAppid(), config.getMinprog().getAppsecret(), code);
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);
        log.info("小程序访问jscode2session = {}", response);
        Map<String,String> map = GoogleGsonUtil.string2Map(response);
        String openid = map.get("openid");
        if (openid == null) throw new IllegalStateException("获取openid失败");
        log.info("小程序用户 code={} openid={}",code,openid);
        return map;
    }

}

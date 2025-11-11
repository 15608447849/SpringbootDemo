package other;

import java.util.Base64;

public class jwt_token {
    public static void main(String[] args) throws Exception{
        // 自定义生成JWT令牌的密钥
        String nacosSecret = "local_nacos_token_12345678909876";
        // 输出密钥长度，要求不得低于32字符，否则无法启动节点。
        System.out.println("len " + nacosSecret.length() +" "+ nacosSecret);

        // 密钥进行Base64编码
        String encoded = Base64.getEncoder().encodeToString(nacosSecret.getBytes());

        System.out.println("base64 " + encoded); // bG9jYWxfbmFjb3NfdG9rZW5fMTIzNDU2Nzg5MDk4NzY

//        `nacos.core.auth.server.identity.key` is missing, please set:  000000

    }
}

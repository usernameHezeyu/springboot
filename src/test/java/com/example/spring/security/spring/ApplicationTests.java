package com.example.spring.security.spring;

import com.example.spring.security.spring.mapper.UserInfoMapper;
import com.example.spring.security.spring.utils.redis.RedisStringUtils;
import com.ulisesbocchio.jasyptspringboot.encryptor.SimpleAsymmetricConfig;
import com.ulisesbocchio.jasyptspringboot.encryptor.SimpleAsymmetricStringEncryptor;
import com.ulisesbocchio.jasyptspringboot.util.AsymmetricCryptography;
import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class ApplicationTests {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private RedisStringUtils redisStringUtils;

    @Autowired
    StringEncryptor stringEncryptor;//密码解码器自动注入

    @Test
        void contextLoads() {
////        UserPojo d = userInfoMapper.findOneByLogin("d");
        redisStringUtils.setKey("kk","ddd");
        String kk = redisStringUtils.getValue("kk");
        log.info(kk);
//        System.out.println(stringEncryptor.encrypt("kaidu123"));
//        BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
//        //加密所需的salt(盐)
//        textEncryptor.setPassword("G0CvDz7oJn6");
//        String password = textEncryptor.encrypt("kaidu123");
//        log.info(password);
    }
    @Test
    public void dd(){
        SimpleAsymmetricConfig config = new SimpleAsymmetricConfig();
        // 设置密钥类型
        config.setKeyFormat(AsymmetricCryptography.KeyFormat.PEM);
        // 设置用来加密的公钥(注:生成的公钥/私钥可能会有换行，保不保留换行都一样)
        config.setPublicKey("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCM+fhzpys45rCsManRl0u5weVTHGekG8GNAu1s\n" +
                "vRkjslrGFqb7RRANKq3+xONX31XXRv5/JskZrm6BZ4ISTV+fFOy2dkBWhCJCJt/T+jIhvdG5Z0zs\n" +
                "G0L1f/RhRnRmtQbSx9vEiICVl+yyRjDtYFmyioq+rwK7dLF69wM7q2tRQwIDAQAB");
        StringEncryptor encryptor = new SimpleAsymmetricStringEncryptor(config);
        String info = "kaidu123";
        log.info("加密前" + info);
        // 加密
        String result = encryptor.encrypt(info);
        log.info("加密后" + result);
        // 解密
        config.setPrivateKey("MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAIz5+HOnKzjmsKwxqdGXS7nB5VMc\n" +
                "Z6QbwY0C7Wy9GSOyWsYWpvtFEA0qrf7E41ffVddG/n8myRmuboFnghJNX58U7LZ2QFaEIkIm39P6\n" +
                "MiG90blnTOwbQvV/9GFGdGa1BtLH28SIgJWX7LJGMO1gWbKKir6vArt0sXr3Azura1FDAgMBAAEC\n" +
                "gYB2Y9JXZbLxlSIXaZmgbvtkI0Yv1rHe6oRPpH65C9DZ3oVGx20xpN0HAamfQ79cYy0WngNJysul\n" +
                "aoxY1hEwx5OdoodubrTtM1LKqFBORMJwd3odnTuqStBHzeb0oiWcfCFNgrPfceCVWnCLQEZ4i+fE\n" +
                "NpkgF1leXpV3avKoTZNowQJBAOhU/Z5vklOQcPxM0daF1uXL2HPr+K5PVFWFZm2W/zcKWdnKpz22\n" +
                "G1fqBsx7IvCoahFDNZiiqgh+tm+eeYuoC6MCQQCbVoJlxmmpppEuUZ20plQETm+MK/plVatS12uX\n" +
                "owyfjnVw/Q1biTBulLUGj5p83SZqFOkyiZaBKbrgEV3Vc/3hAkEAmDiPTw405jFeBcBjP9XDHh63\n" +
                "/AOglLx/OeVEXgsvviU3B35Coyk8sxYvTCxlkR4XGKZbaF0zYIt9BpbjdgAtVwJAGca6Lp9FC1TL\n" +
                "hI8gh0iOaoSFeBIYI9aabk222dvBjXx7OnuTStwyADeG7obH0O51oGbF54/zPI2rJ+FZ28C6oQJB\n" +
                "AJqzSzQisnvAzm54f6zuGwCQTGIvsp+uy9uWsu3YjKDwVzQeTLaZ0nVxJNiU3GvdCeL+lel9IRsW\n" +
                "1E5oF4yofog=");
        String originInfo = encryptor.decrypt(result);
        log.info("解密后" + originInfo);

    }


}

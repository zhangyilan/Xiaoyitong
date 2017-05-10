package ui.test.cn.xiaoyitong.service;

import com.alipay.sdk.app.EnvUtils;

import java.util.Map;
import java.util.UUID;

/**
 * Created by John on 2017/4/11.
 */

public class PayUntils{
    /**
     * 支付宝支付业务：入参app_id
     */
    public static final String APPID = "2016080400161558";

    /**
     * 支付宝账户登录授权业务：入参pid值
     */
    public static final String PID = "2088102169842313";
    /**
     * 支付宝账户登录授权业务：入参target_id值
     * 用来标志每次请求支付宝的唯一请求，每次请求生成一个UUID
     */
    public static final String TARGET_ID = UUID.randomUUID().toString();

    /** 商户私钥，pkcs8格式 */
    /** 如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个 */
    /** 如果商户两个都设置了，优先使用 RSA2_PRIVATE */
    /** RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议使用 RSA2_PRIVATE */
    /** 获取 RSA2_PRIVATE，建议使用支付宝提供的公私钥生成工具生成， */
    /**
     * 工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1
     */
    public static final String RSA2_PRIVATE = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCGdBlM/50dSEgeU4NGnt690YlDSPgCvfDAjWWfTyH3kREDk69R2kr7De2sajNdCxWuyxQBNTCCqEduCS4DWOAGcYUU8cURged+0ZqG5H2Jdp/RmAFmwpQHVh2bAFpcOeEa6OL9na8LxI7QCrRC4Xh8++qkeWU2YIzPeRlZzUoJUZ6eix0IS/wl7to3Yfa6Kel5eXIWw56+Z6mEyEsqz3jmJAtxrSmbtCgHJ25OlytddUWVks49Wq2TxGehg81vRODRn3JX+ccTDezpU5pHxhzLzkGgLK2r3M4rG0hLoPE/DhDNLZenj0eK2QfeowLE5FtscOAjmYKWCsDA84PTXFcZAgMBAAECggEAb5Uobitww516JJjgSJqOdm/P/6QuF3B0vghavwSYRvIAcbx98ms8cO/VQJJ+gAgz/XUR7BKtaLGpXhuGMUbv3MkXThN7nhT1/tyK5sn3mT52UiZZe2OeIByKw1lepE1wkLmXsCzN2fFNKEDJ6vtd2DPlPox0v9UO5xw0Or/RYrtC/AMsB9bLKG7V9Lm/walaH3PjZJO9K/GNGtAquzByENznq4camlsZd47Yek9gPkjqEaDl9CrMXsQlr1IbPut2yRXHxdzsVpcB27l1Zopt/pNj5etyvoRocgW75hlfga+8nsZcgDm8MWIKdNoAsJHn7eNl1VDVl1P4UW7IPFeQAQKBgQC6S95gOuXqikFXl0bG/DbRNS/YG0Zo+hL9JXyd6/Kx3YPKW9RCPHAjEuS8A8vPNqwsJ53bvG095Yz/foa4pO5Cow2XF5Zsxdky5kEk2h4lNNXjc4/7bZ1T+bu6u44yFx7S3KCTEjR7iWTwsF3fw4wEUK5pNY9sN8c32FM80QkZGQKBgQC4wolhK22Or7vOyyDH2JqX3Etu5NIDpTStETBquhYkprNQfSAtw+/WpSzB+rQdcabWLPvdE+salUDvnbEpeg9ZMPZ+ctP1RY0P9vqPv7wU5i80eGLC7KF4FQmKaPVJ2hxQKrQxC85Fu7dG0uYabAg2pqzzcOocDaWwJc4XLq7uAQKBgQCB+QqVgqIDpx99V7p2e2JXRU8y65yNAuOan7u4hfQVqaEX3kdRSs9FeRoCHnfkApXOXHli46wnge6EFicxzUe5T/7cSgWVr9cgDsqmRlK/zIPpIyfx14DrJJGX7uB/C+hiXrDcyte41JuK3NExm0ElhZCZMFf2vVeNG6y/AmvhiQKBgQCtMhfMUgSKnGwGx138WS1aLDU+SBwjNKvzjMiBU93MD8TKL/GJVUKwSPhzAsvzSgXmgha8QJ7EtaNAu+dGhVh7+K0pDgVpyAAbNh/dvxPV143qtuuF5ILc6YOfDxLZXy/pao6t524+6mA9cRgpLppoeMhqELA1CgoG3Pmbu7RaAQKBgHi2YDlGf5jUhxPQR0813jO6NENYggSbKbH2LRBGNu8sbEBRiUG+XiOQ7uFK93AYmaxWx3nvI0P/W5WjImpr++PWamAnYHqhf0t8lubx5x5Ylqa4teArw9qmIBSN9DjE/qWLehSsb+HMLibVRno807dzMdlxAM8ounIZfgM+5PWo";
    public static final String RSA_PRIVATE = "";




    public static String PayV2(String name, String bewrite, String price) {

        /**
         * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
         * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
         * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险；
         *
         * orderInfo的获取必须来自服务端；
         */
        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2,name,bewrite,price);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
        final String orderInfo = orderParam + "&" + sign;
        return orderInfo;
    }

}

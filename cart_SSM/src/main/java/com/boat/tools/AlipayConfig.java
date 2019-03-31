package com.boat.tools;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {

//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String app_id = "2016092700608625";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCJm6yjLv4l6FvFek69iG7WM6pYgTrihZkIsHPYMYR0i50EUfJLR5o5rWzBejYkUPlqd6JwiawsvNYbT5oP7SlCdHezmESth8GyMeuukQzryAdNzwcvL8P6jLxitwJN2YP0FVBlfmWs0fXuKWKKFiDpMhCzTSJRfFboSf6uCiDAAri6gfzQU0pm7CxSrHPr3F4NzgrY5FCATZiuvuNa0+KItjH7E+pYq/VP8MP0dyd+TWHygDvT74cWsbC0jjj7kYVdiYBL82QhxM8wxxNH6SR7CWxcB9qCnvbRwpQ2mOebaDL+w37LCfYhxrm0WvIxSe7ntioyGsXzY+AOTvOsnN23AgMBAAECggEACbJIhe8nxXAMF2w45C4a8UkOtdHVXmTflbcpUpCarsOvusAzfI59FZdc1H1SF1kFWjU34wowca6Uv7PqPTkO8TnXvXX7UOcCBRZzbweVi3+61AbEkNSi0KLeG26d4PJ009w0oBI6A+V/mNqdsPFCPGxktN24j1ldnlyob4cTK7RqZfZySu4DTXuHZ91x2FLMxWdZCPCsHfPPZ84HYPg8zeXnEhy/3bDlXVgHGQdc+XwMCS7ct6yEdeiLDaEcUBs2c0Z/goj8Hr2bojLe7bU6W9Inb9N9lnrIMIKT5+JstUpe0FVVHCk3UKLrZ9z0w/fPY2MTAl2+eHSyTtAsI1wrsQKBgQDlrFHDK85a+nBXFBFg59aHcg1SIvPrIR9N9cBAvULh09mxoWHacEKRTgZfiU4k2fYYuzLk4n+XiQ+CJh8JiTPYnfwkEakd7OvtxMmhLZ7CTp+2GgaxL1Mb+SVecF1Hh3dEjSeQHpV75xBpHwmlGFQ8ZJQmQXDe25oBmp3FUPGMQwKBgQCZYbzn2tQx3PifukpECIhiEn0Q8pn4ITVP9HPe36qgWj2uXF3NQBFpbUWdPJGsAU3/3MgwjV0Uj3ZyESQ9dbfkfTL1D5PbHujvn70UDWvAd1U99uTPof5uHxXQab+jpDwYiRsFUb6tytSw4ugpPC9GWN2WGTNV881De20qK8GLfQKBgQDdcAYo6cx8tSepHWUgDIcvcgxb2+1XD0lSbhXdlVytxQcIF3P0GvZjiFnmtMiOCUQYVizct3CTVweH0V9tX0aInNHkqM5oVuymA2zvpL+D0jB0b6VyIhG8T7vy0N1uwLKCeqHhd0ti+cY9xDoVXl6hF8wrEj9T72EOGNfyzXcGZwKBgDNOUEQ2HLi5M6kqRWFQgV5c1NzwabRoV07xdDjfFFIjHXLPjsjzX1+mn5yjAA9LOG4XyhcDklkoS/SH61lroSGbB4qVtV2cj0poYnTssFhnEmpxxcP2agcn+sIGPYZdj4SdiB5VY7T6nf61R/dTHHUpkKV+tH9R2B10keWDUXB5AoGBANhbTGnFoEsXuTVKk8bYJ3uQp/vi8w5EqK/mI72PN4RLoBVUSNryvk1bqO95tE5ag30c/PZx5pLKF7oR/60OO6JG/edzwcEwBehE6L3p5x/ECjBgMyx0JFrX2WUqnyiD81dZcWB3Dj0HXzjuy0cq0iqmabJAAwRHU25XAvzLBYXb";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA2EhsbiUVD07Ez8G+f0ZHOBd/v1Coks6aH3n2KPW0JIRln5LsRhPsCxG7Wr8MZSGeKkqz8LtnNUWq1aVLpYs3/WsohrDd+LzxCQG1/mY5jCXTlJ4lZvx1QBJJuCKmtv4bPYl8aQ60XjqANPD22N+4r6D1LMxwyHBpHf3E+OWHcsunfPdBXp4G6V/5a7PApqNNegnWNmPWMRfBIstFvxS3ukay7txGG3w3l0aE7fABfjVNaEXsK16dOLOm5MnGrTmK6RM4VRMt1btwdWFhBEg5YAqquriPOYE6nukzjuoCtFX6MJj0nx35Lgoms+oj1ZJD1wnXGG9QbD5JCu8eEydYywIDAQAB";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://www.boatfu.top/alipayNotifyNotice";
    //notify_url.jsp
    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url = "http://www.boatfu.top/alipayReturnNotice";
    //return_url.jsp
    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "utf-8";

    // 支付宝网关
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    // 支付宝网关
    //public static String log_path = "C:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
//    public static void logResult(String sWord) {
//        FileWriter writer = null;
//        try {
//            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
//            writer.write(sWord);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (writer != null) {
//                try {
//                    writer.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
}


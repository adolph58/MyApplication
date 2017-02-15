
package cn.adolphc.mywifi.util;

public class GlobalConsts {
    // 本地化存储对象的文件名
    public static final String USER_INFO_NAME = "wifiuser.info";

    // 修改昵称
    public static final int USER_INFO_MODIFY_NICKNAME = 1;

    // 修改年龄
    public static final int USER_INFO_MODIFY_AGE = 2;

    // 修改个性签名
    public static final int USER_INFO_MODIFY_SIGNATURE = 3;

    // 修改职业
    public static final int USER_INFO_MODIFY_OCCUPTION = 4;

    // 请求成功返回码
    public static final int RESPONSE_CODE_SUCCESS = 200;

    // URL前缀
    public static final String BASEURL = "http://jaunty1.java.cdnjsp.cn/";

    // 注册URL
    public static final String URL_USER_REGIST = BASEURL + "user/regist";

    // 登录URL
    public static final String URL_USER_LOGIN = BASEURL + "user/login";

    // 认领热点 URL
    public static final String URL_WIFI_CLAIM = BASEURL + "wifi/claim";

    // 获取热点密码 URL
    public static final String URL_WIFI_GET_PWD = BASEURL + "wifi/getwifipwd";


}

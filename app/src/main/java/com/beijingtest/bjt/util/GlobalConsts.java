
package com.beijingtest.bjt.util;

public class GlobalConsts {
    // 本地化存储对象的文件名
    public static final String USER_INFO_NAME = "bjtuser.info";

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
    public static final String BASEURL = "http://121.8.149.62:8888/";

    //获取客户列表
    public static final String GET_CUSTOM_LIST = "http://192.168.1.73:8989/Api/phone/List.aspx?State=cust";

    // 注册URL
    public static final String URL_USER_REGIST = BASEURL + "user/regist";

    // 登录URL
    public static final String URL_USER_LOGIN = BASEURL + "user/login";

    // 修改用户信息
    public static final String URL_USER_UPDATE_USER_INFO = BASEURL + "user/update";

    // App版本更新
    public static final String URL_APP_UPDATE = BASEURL + "FuJian/PhoneAPKVersion/";

    // App版本检查
    public static final String URL_APP_CHECK_VERSION = BASEURL + "Api/PhoneAPKVersion/List.aspx";

    // 图片选择器拍照和相册选取区别码
    public static final int PIC_CHOICE_CAMERA = 0;
    public static final int PIC_CHOICE_ALBUM = 1;

    // 性别选择器男和女选取区别码
    public static final int GENDER_CHOICE_MALE = 0;
    public static final int GENDER_CHOICE_FEMALE = 1;

}
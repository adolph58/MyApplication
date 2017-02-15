package cn.adolphc.mywifi.util;

import android.util.Log;
import cn.adolphc.mywifi.MyApplication;
//日志框架 baidu android log4j 
public class LogUtil {
    public static void i(String tag,Object msg) {
        // 运行在真机上不执行log.i,开发阶段执行log.i
        if(MyApplication.isRelease) {
            return;
        }else {
            Log.i(tag,String.valueOf(msg));
        }
    }
}

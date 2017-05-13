package ui.test.cn.xiaoyitong.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 用于弹出更新对话框进行下载安装
 * Created by asus on 2017/3/10.
 */

public class Myutils {
    /**
     * 获得版本号
     */
    public static String getVersion(Context context){
        //packagemanager 可以获得清单文件中的所有信息
        PackageManager manager = context.getPackageManager();
        //getPackageName()是获取清单文件中的所偶信息
        try {
            //getPackageName()获取当前程序的包名
            PackageInfo packageInfo = manager.getPackageInfo(context.getPackageName(),0);
            return packageInfo.versionName;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager networkInfo = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (networkInfo != null) {
            NetworkInfo info = networkInfo.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                // 当前网络是连接的
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    // 当前所连接的网络可用
                    return true;
                }
            }
        }

        return false;

    }
}

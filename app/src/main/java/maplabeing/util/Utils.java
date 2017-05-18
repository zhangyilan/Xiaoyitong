package maplabeing.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by renba on 2017/5/18.
 */

public class Utils {
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

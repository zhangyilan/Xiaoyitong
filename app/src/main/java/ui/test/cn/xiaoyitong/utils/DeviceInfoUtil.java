package ui.test.cn.xiaoyitong.utils;

import android.content.Context;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import java.util.UUID;

/**
 * Created by Administrator on 2017/4/20.
 */

public class DeviceInfoUtil {
    /**
     * 得到设备AndroidID，需要设备添加 Google账户
     *
     * @param context
     * @return
     */
    public static String getAndroidID(Context context) {
        String androidID = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        return androidID;
    }

    /**
     * 得到设备IMEI值
     *
     * @param context
     * @return
     */
    public static String getIMEI(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }

    /**
     * 得到设备序列号
     *
     * @param context
     * @return
     */
    public static String getSimSerialNumber(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getSimSerialNumber();
    }

    /**
     * 得到设备唯一识别码
     *
     * @param context
     * @return
     */
    public static String getUniqueNumber(Context context) {
        String androidID = getAndroidID(context);
        String imei = getIMEI(context);
        String simSerialNumber = getSimSerialNumber(context);
        UUID uuid = new UUID(androidID.hashCode(),
                ((long) imei.hashCode() << 32) | simSerialNumber.hashCode());
        return uuid.toString();
    }
}

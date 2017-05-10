package ui.test.cn.xiaoyitong.InternetUtils;

import android.widget.Toast;


/**
 * Created by John on 2017/4/17.
 */

public class ExpressDetailedUtils {
    public static String MosaicExpressInformation(String nickName, String type, String specifications, String pickNumber,

                                           String phoneNumber, String name, String address, String urgent, String price){
        String info=null;
        info = "http://123.206.92.38:80/SimpleSchool/expressservlet?opt=insert_express&format="
                + specifications
                + "&name="
                + type
                + "&phone="
                + phoneNumber
                + "&code="
                + pickNumber
                + "&express_user="
                + nickName
                + "&price="
                + price
                + "&user_address="
                + address
                + "&real_name="
                + name
                + "&emergency="
                + urgent;
        return info;
    }
}

package ui.test.cn.xiaoyitong.httpHelper;

/**
 * Created by asus on 2017/4/13.
 */

public interface HttpCallback {
    void onFinish(String respose);
    void onerror(Exception e);
}

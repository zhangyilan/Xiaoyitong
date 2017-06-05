package ui.test.cn.xiaoyitong.httpHelper;

import org.json.JSONException;

/**
 * Created by lenovo on 2016/12/20.
 */

public interface HttpCallBackListener {
    void onFinish(Object respones) throws JSONException;
    void onError(Exception e);
}

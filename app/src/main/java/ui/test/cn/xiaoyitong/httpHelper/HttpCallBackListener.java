package ui.test.cn.xiaoyitong.httpHelper;

/**
 * Created by lenovo on 2016/12/20.
 */

public interface HttpCallBackListener {
    void onFinish(Object respones);
    void onError(Exception e);
}

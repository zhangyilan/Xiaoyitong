package ui.test.cn.xiaoyitong.InternetUtils;

public interface HttpCallbackListener {
	
	void onFinish(String response);
	void onError(Exception e);
}

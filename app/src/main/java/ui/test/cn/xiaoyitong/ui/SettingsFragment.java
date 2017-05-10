package ui.test.cn.xiaoyitong.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.easemob.EMCallBack;
import com.easemob.chat.EMChatManager;

import ui.test.cn.xiaoyitong.GetContext.MyApplication;
import ui.test.cn.xiaoyitong.R;
import ui.test.cn.xiaoyitong.entity.Constant;

/**
 * Created by YanChunlin on 2017/4/19.
 */

//设置界面
public class SettingsFragment extends Fragment implements View.OnClickListener {
    //退出按钮
    private Button logoutBtn;
    ThirstFragment thirstFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_conversation_settings, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        thirstFragment = new ThirstFragment();
        if(savedInstanceState != null && savedInstanceState.getBoolean("isConflict", false))
            return;
        logoutBtn = (Button) getView().findViewById(R.id.btn_logout);
        if(!TextUtils.isEmpty(EMChatManager.getInstance().getCurrentUser())){
            logoutBtn.setText("退出登录" + "(" + EMChatManager.getInstance().getCurrentUser() + ")");
        }
        logoutBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_logout: //退出登陆
                logout();
                SharedPreferences share = getActivity().getSharedPreferences("user",getActivity().MODE_PRIVATE);
                share.edit().clear().commit();
                break;
            default:
                break;
        }

    }

    void logout() {
        final ProgressDialog pd = new ProgressDialog(getActivity());
        String st = getResources().getString(R.string.Are_logged_out);
        pd.setMessage(st);
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        MyApplication.getInstance().logout(new EMCallBack() {

            @Override
            public void onSuccess() {
                getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        pd.dismiss();
                        // 重新显示登陆页面
                        getActivity().finish();
                        startActivity(new Intent(getActivity(), LoginActivity.class));

                    }
                });
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {

            }
        });
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(thirstFragment.isConflict){
            outState.putBoolean("isConflict", true);
        }else if(thirstFragment.getCurrentAccountRemoved()){
            outState.putBoolean(Constant.ACCOUNT_REMOVED, true);
        }
    }
}

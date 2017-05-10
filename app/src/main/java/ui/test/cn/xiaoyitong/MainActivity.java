package ui.test.cn.xiaoyitong;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import java.util.Timer;
import java.util.TimerTask;

import ui.test.cn.xiaoyitong.ui.BeasActivity;


public class MainActivity extends AppCompatActivity {
    private static final int sleepTime = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);// 去掉
        }

        setContentView(R.layout.activity_main_welcome);
        // 你要转向的Activity

        // 第一步，得到Timer的实例化对象
        Timer timer = new Timer(); // 计时器
        // 第二步，实例化TimerTask对象
        TimerTask task = new TimerTask() {
            @Override
            // 实例化TimerTask对象的时候，需要重写它的run()方法，然后在这个方法体内增加需要执行的具体操作
            public void run() {
                Intent it = new Intent(getBaseContext(), BeasActivity.class);
                startActivity(it); // 执行
            }
        };
        // 第三步，启动定时器
        timer.schedule(task, 1000 * 2); // 2秒后

        //接受参数，获取对象并提交
        SharedPreferences spre=getSharedPreferences("first", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor=spre.edit();
        editor.putBoolean("status",false);
        editor.commit();//提交
    }


    /*@Override
    protected void onStart() {
        super.onStart();
        new Thread(new Runnable() {
            public void run() {
                // 先判断是否已经登录了
                if (HXSDKHelper.getInstance().isLogined()) {
                    long start = System.currentTimeMillis();
                    EMGroupManager.getInstance().loadAllGroups();
                    EMChatManager.getInstance().loadAllConversations();
                    long costTime = System.currentTimeMillis() - start;
                    // 等待sleeptime时长
                    if (sleepTime - costTime > 0) {
                        try {
                            Thread.sleep(sleepTime - costTime);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    // 进入主页面
                    startActivity(new Intent(MainActivity.this, BeasActivity.class));

                } else {
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                    }
                    startActivity(new Intent(MainActivity.this, FirstActivity.class));

                }
            }
        }).start();

    }
*/

}

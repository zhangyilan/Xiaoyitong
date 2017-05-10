package ui.test.cn.xiaoyitong.backgroundlbs.recevier;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import ui.test.cn.xiaoyitong.backgroundlbs.service.LocationService;

import static android.content.Context.MODE_PRIVATE;


public class BootCompletReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        SharedPreferences preferences = context.getSharedPreferences("data", MODE_PRIVATE);
        String name = preferences.getString("name", "");
        if (name.equals("")) {
            Toast.makeText(context, "开机启动注册广播", Toast.LENGTH_SHORT).show();
            context.startService(new Intent(context, LocationService.class));
            Toast.makeText(context, "name为空", Toast.LENGTH_LONG).show();
        }
    }

}

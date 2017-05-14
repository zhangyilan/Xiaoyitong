package ui.test.cn.xiaoyitong.ui;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;
import ui.test.cn.xiaoyitong.R;

public class FeedBackActivity extends SwipeBackActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);;
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        ImageView  back = (ImageView) findViewById(R.id.back);
        TextView  biaoti = (TextView) findViewById(R.id.biaoti);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        biaoti.setText("意见反馈");
    }
}

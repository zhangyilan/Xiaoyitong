package ui.test.cn.xiaoyitong.ui.sonfragmeng;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;
import ui.test.cn.xiaoyitong.Navi.WalkActivity;
import ui.test.cn.xiaoyitong.R;

public class BaodaoActivity extends SwipeBackActivity {
    private Button boxueguan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_baodao);
        TextView biaoti = (TextView) findViewById(R.id.biaoti);
        ImageView back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        biaoti.setText("报到流程");
        boxueguan = (Button) findViewById(R.id.boxueguan);
        boxueguan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BaodaoActivity.this, WalkActivity.class);
                intent.putExtra("x", "30.689492");
                intent.putExtra("y", "103.817126");
                startActivity(intent);
            }
        });
    }
}

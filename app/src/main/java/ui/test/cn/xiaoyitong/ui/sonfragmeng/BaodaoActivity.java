package ui.test.cn.xiaoyitong.ui.sonfragmeng;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;
import ui.test.cn.xiaoyitong.Navi.WalkActivity;
import ui.test.cn.xiaoyitong.R;
import ui.test.cn.xiaoyitong.ui.Tabfour;
import ui.test.cn.xiaoyitong.ui.Tabone;
import ui.test.cn.xiaoyitong.ui.Tabthree;
import ui.test.cn.xiaoyitong.ui.Tabtwo;

public class BaodaoActivity extends Activity {
    private TextView de1;
    private TextView de2;
    private TextView de3;
    private TextView de4;

    private TextView rxxz;
    private TextView bxg;
    private TextView zs;
    private TextView st;

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
        biaoti.setText("报到指南");
        de1 = (TextView) findViewById(R.id.xx1);
        de2 = (TextView) findViewById(R.id.xx2);
        de3 = (TextView) findViewById(R.id.xx3);
        de4 = (TextView) findViewById(R.id.xx4);
        rxxz = (TextView) findViewById(R.id.rxxz);
        bxg = (TextView) findViewById(R.id.bxg);
        zs = (TextView) findViewById(R.id.zs);
        st = (TextView) findViewById(R.id.st);
        //入学须知详情
        de1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(BaodaoActivity.this,"这是入学须知详情",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(BaodaoActivity.this,Tabone.class);
                intent.putExtra("name1","入学须知");
                startActivity(intent);
            }
        });
        //博学馆详情
        de2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(BaodaoActivity.this,"这是博学馆详情",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(BaodaoActivity.this,Tabtwo.class);
                intent.putExtra("name2","博学馆");
                startActivity(intent);
            }
        });
        //住宿管理中心详情
        de3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(BaodaoActivity.this,"这是住宿管理中心详情",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(BaodaoActivity.this,Tabthree.class);
                intent.putExtra("name3","住宿管理中心");
                startActivity(intent);
            }
        });
        //一食堂详情
        de4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(BaodaoActivity.this,"这是一食堂详情",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(BaodaoActivity.this,Tabfour.class);
                intent.putExtra("name4","一食堂");
                startActivity(intent);
            }
        });
    }
}

package ui.test.cn.xiaoyitong.ui.sonfragmeng;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import ui.test.cn.xiaoyitong.R;


/**
 * Created by lenovo on 2017/05/11.
 */

public class ComputerTwoScoreMainActivity extends Activity {
    TextView idCardtextView, nametextView, examNametextView, leveltextView, allscorcetextView;
    private TextView biaoti;
    private ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        setContentView(R.layout.computer_two_selectscore_main_layout);
        biaoti= (TextView) findViewById(R.id.biaoti);
        biaoti.setText("二级查询");
        back= (ImageButton) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        biaoti= (TextView) findViewById(R.id.biaoti);
        biaoti.setText("二级成绩详情");
        back= (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        idCardtextView = (TextView) findViewById(R.id.computer_two_scorce_idCard);
        nametextView = (TextView) findViewById(R.id.computer_two_scorce_name);
        examNametextView = (TextView) findViewById(R.id.computer_two_scorce_examName);
        leveltextView = (TextView) findViewById(R.id.computer_two_scorce_level);
        allscorcetextView = (TextView) findViewById(R.id.computer_two_scorce_allscorce);
        Intent intent = getIntent();
        String idCard = intent.getStringExtra("idCard");
        String name = intent.getStringExtra("name");
        String examName = intent.getStringExtra("examName");
        String level = intent.getStringExtra("level");
        String scorce = intent.getStringExtra("scorce");
        idCardtextView.setText(idCard);
        nametextView.setText(name);
        examNametextView.setText(examName);
        leveltextView.setText(level);
        allscorcetextView.setText(scorce);

    }
}

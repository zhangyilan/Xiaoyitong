package ui.test.cn.xiaoyitong.ui.sonfragmeng;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;
import ui.test.cn.xiaoyitong.R;
import ui.test.cn.xiaoyitong.adapter.SoreAdapter;
import ui.test.cn.xiaoyitong.entity.Grade;
import ui.test.cn.xiaoyitong.utils.Utils;

public class MenuGandFind_detail extends SwipeBackActivity {
    private List<Grade> grades;
    private ListView grade;
    Button btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_grandfind_detail);
        grades = (List<Grade>) getIntent().getSerializableExtra("grades");
        SoreAdapter adapter = new SoreAdapter(MenuGandFind_detail.this, R.layout.menu_grand_item, grades);
        grade = (ListView) findViewById(R.id.grade);
        grade.setAdapter(adapter);

        Utils.setListViewHeight(grade);

        btn_back = (Button) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

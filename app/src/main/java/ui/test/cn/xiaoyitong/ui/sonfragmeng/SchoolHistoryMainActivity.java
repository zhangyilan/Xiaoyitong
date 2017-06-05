package ui.test.cn.xiaoyitong.ui.sonfragmeng;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.dsw.calendar.component.MonthView;
import com.dsw.calendar.entity.CalendarInfo;
import com.dsw.calendar.views.GridCalendarView;

import java.util.ArrayList;
import java.util.List;

import ui.test.cn.xiaoyitong.R;

public class SchoolHistoryMainActivity extends Activity {
    private GridCalendarView gridCalendarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school_history_main);

        List<CalendarInfo> list = new ArrayList<CalendarInfo>();
        list.add(new CalendarInfo(2017,2,27,"开学报到"));
        list.add(new CalendarInfo(2017,2,28,"开学报到"));

        list.add(new CalendarInfo(2017,3,1,"开学报到"));
        list.add(new CalendarInfo(2017,3,2,"开学报到"));
        list.add(new CalendarInfo(2017,3,3,"开学报到"));
        list.add(new CalendarInfo(2017,3,4,"补考"));
        list.add(new CalendarInfo(2017,3,5,"补考"));
        list.add(new CalendarInfo(2017,3,6,"正式行课"));

        list.add(new CalendarInfo(2017,4,2,"清明节",1));
        list.add(new CalendarInfo(2017,4,3,"清明节",1));
        list.add(new CalendarInfo(2017,4,4,"清明节",1));

        list.add(new CalendarInfo(2017,4,29,"劳动节",1));
        list.add(new CalendarInfo(2017,4,30,"劳动节",1));

        list.add(new CalendarInfo(2017,5,1,"劳动节",1));
        list.add(new CalendarInfo(2017,5,28,"端午节",1));
        list.add(new CalendarInfo(2017,5,29,"端午节",1));
        list.add(new CalendarInfo(2017,5,30,"端午节",1));

        list.add(new CalendarInfo(2017,6,26,"期末考试"));
        list.add(new CalendarInfo(2017,6,27,"期末考试"));
        list.add(new CalendarInfo(2017,6,28,"期末考试"));
        list.add(new CalendarInfo(2017,6,29,"期末考试"));
        list.add(new CalendarInfo(2017,6,30,"期末考试"));

        list.add(new CalendarInfo(2017,7,3,"期末考试"));
        list.add(new CalendarInfo(2017,7,4,"期末考试"));
        list.add(new CalendarInfo(2017,7,5,"期末考试"));
        list.add(new CalendarInfo(2017,7,6,"期末考试"));
        list.add(new CalendarInfo(2017,7,7,"期末考试"));
        list.add(new CalendarInfo(2017,7,10,"社会实践"));
        list.add(new CalendarInfo(2017,7,11,"社会实践"));
        list.add(new CalendarInfo(2017,7,12,"社会实践"));
        list.add(new CalendarInfo(2017,7,13,"社会实践"));
        list.add(new CalendarInfo(2017,7,14,"社会实践"));

        list.add(new CalendarInfo(2017,9,4,"开学报到"));
        list.add(new CalendarInfo(2017,9,5,"开学报到"));
        list.add(new CalendarInfo(2017,9,6,"开学报到"));
        list.add(new CalendarInfo(2017,9,7,"教师节表彰"));
        list.add(new CalendarInfo(2017,9,8,"开学报到"));
        list.add(new CalendarInfo(2017,9,9,"补考"));
        list.add(new CalendarInfo(2017,9,10,"补考"));
        list.add(new CalendarInfo(2017,9,4,"正式行课"));

        list.add(new CalendarInfo(2017,10,1,"国庆节",1));
        list.add(new CalendarInfo(2017,10,2,"国庆节",1));
        list.add(new CalendarInfo(2017,10,3,"国庆节",1));
        list.add(new CalendarInfo(2017,10,4,"中秋节",1));
        list.add(new CalendarInfo(2017,10,5,"国庆节",1));
        list.add(new CalendarInfo(2017,10,6,"国庆节",1));
        list.add(new CalendarInfo(2017,10,7,"国庆节",1));
        list.add(new CalendarInfo(2017,10,8,"国庆节",1));

        list.add(new CalendarInfo(2017,11,6,"消防安全周"));
        list.add(new CalendarInfo(2017,11,7,"消防安全周"));
        list.add(new CalendarInfo(2017,11,8,"消防安全周"));
        list.add(new CalendarInfo(2017,11,9,"消防安全周"));
        list.add(new CalendarInfo(2017,11,10,"消防安全周"));
        list.add(new CalendarInfo(2017,11,11,"消防安全周"));
        list.add(new CalendarInfo(2017,11,12,"消防安全周"));

        list.add(new CalendarInfo(2017,12,7, "12.9表彰"));
        list.add(new CalendarInfo(2017,12,28,"跨年晚会"));

        list.add(new CalendarInfo(2018,1,1,"元旦节"));
        list.add(new CalendarInfo(2018,1,2,"期末考试"));
        list.add(new CalendarInfo(2018,1,3,"期末考试"));
        list.add(new CalendarInfo(2018,1,4,"期末考试"));
        list.add(new CalendarInfo(2018,1,5,"期末考试"));
        list.add(new CalendarInfo(2018,1,8,"期末考试"));
        list.add(new CalendarInfo(2018,1,9,"期末考试"));
        list.add(new CalendarInfo(2018,1,10,"期末考试"));
        list.add(new CalendarInfo(2018,1,11,"期末考试"));
        list.add(new CalendarInfo(2018,1,12,"期末考试"));
        list.add(new CalendarInfo(2018,1,15,"社会实践"));
        list.add(new CalendarInfo(2018,1,16,"社会实践"));
        list.add(new CalendarInfo(2018,1,17,"社会实践"));
        list.add(new CalendarInfo(2018,1,18,"社会实践"));
        list.add(new CalendarInfo(2018,1,19,"社会实践"));

        gridCalendarView = (GridCalendarView) findViewById(R.id.gridMonthView);
        gridCalendarView.setCalendarInfos(list);
        gridCalendarView.setDateClick(new MonthView.IDateClick(){

            @Override
            public void onClickOnDate(int year, int month, int day) {
                Toast.makeText(SchoolHistoryMainActivity.this,"点击了" +  year + "-" + month + "-" + day,Toast.LENGTH_SHORT).show();
            }
        });
    }
}

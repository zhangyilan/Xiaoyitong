package ui.test.cn.xiaoyitong.ui;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.util.ArrayList;
import java.util.List;

import ui.test.cn.xiaoyitong.R;
import ui.test.cn.xiaoyitong.adapter.MoodAdapter;
import ui.test.cn.xiaoyitong.entity.Mood;
import ui.test.cn.xiaoyitong.utils.PullToZoomListView;

/**
 * Created by asus on 2017/4/2.
 */

public class SecondFragment extends Fragment {

    private View view;

    PullToZoomListView listView;
    private String[] adapterData;
    private TextView txt_send;
    private List<Mood> mood = new ArrayList<Mood>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab02, container, false);
        initview(view);
        return view;

    }

    private void initview(View view) {
        initMood();
        MoodAdapter adapter = new MoodAdapter(getActivity(), R.layout.item_mood, mood);
        listView = (PullToZoomListView) view.findViewById(R.id.listview);
        listView.setAdapter(adapter);
        //adapterData = new String[] { "1","2","3","4","5","6","7","8","9","10" };
        //listView.setAdapter(new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, adapterData));
        listView.getHeaderView().setImageResource(R.drawable.splash01);
        listView.getHeaderView().setScaleType(ImageView.ScaleType.CENTER_CROP);
    }

    private void initMood() {
        Mood m1 = new Mood("20150116", "1小时前", "外星人？秘鲁发现两具巨头干尸！");
        mood.add(m1);
        Mood m2 = new Mood("20150594", "9小时前", "太空旅馆住5天700万台币");
        mood.add(m2);
        Mood m3 = new Mood("20150664", "5小时前", "发现新物种30年科学家至今无法归类");
        mood.add(m3);
        Mood m4 = new Mood("20150393", "昨天", "外星人阻止了苏联核电站爆炸？");
        mood.add(m4);
        Mood m5 = new Mood("20150589", "前天", "十大在亚洲及澳洲最好玩的主题公园");
        mood.add(m5);
        Mood m6 = new Mood("20151252", "刚刚", "看了这些你就知道人类在宇宙中是多么渺小");
        mood.add(m6);
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }*/

}

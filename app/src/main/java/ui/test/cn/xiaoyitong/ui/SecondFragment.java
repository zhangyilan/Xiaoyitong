package ui.test.cn.xiaoyitong.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
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

    ListView listView;
    private String[] adapterData;
    private TextView txt_send;
    private List<Mood> mood = new ArrayList<Mood>();
    private FloatingActionButton mFab;
    private MoodAdapter adapter;
    Button btn_pop;
    private TextView biaoti;
    private ImageView back;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab02, container, false);
        initview(view);
        return view;

    }

    private void initview(View view) {

        biaoti= (TextView) view.findViewById(R.id.tv_title);
        biaoti.setText("论坛");


        initMood();
        adapter = new MoodAdapter(getActivity(), R.layout.item_mood, mood);
//        listView = (PullToZoomListView) view.findViewById(R.id.listview);
        listView = (ListView) view.findViewById(R.id.listview);
        listView.setAdapter(adapter);
//        listView.getHeaderView().setImageResource(R.drawable.splash01);
//        listView.getHeaderView().setScaleType(ImageView.ScaleType.CENTER_CROP);
        mFab = (FloatingActionButton) view.findViewById(R.id.tl_fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PublishActivity.class);
                startActivityForResult(intent, 1);
            }
        });


        btn_pop = (Button) view.findViewById(R.id.button_pop);
        btn_pop.setVisibility(View.GONE);

        Button btn_add = (Button) view.findViewById(R.id.button);
        // 进入添加好友页
        btn_add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddContactActivity.class));
            }
        });
    }

    private void initMood() {
        Mood m1 = new Mood("20150116", "15分钟前", R.drawable.img11, "天气好热啊，好想室友给我买一个冰激凌啊！");
        mood.add(m1);
        Mood m2 = new Mood("20150594", "1小时前", R.drawable.img12, "你们看过最好看的电影是什么啊，求推荐！");
        mood.add(m2);
        Mood m3 = new Mood("20150664", "5小时前", R.drawable.img13, "今年暑假有没有想去云南的童鞋啊，求组队。。。");
        mood.add(m3);
        Mood m4 = new Mood("20150393", "昨天", R.drawable.img16, "暑假想去找一个兼职啊！！！不想在家发霉！");
        mood.add(m4);
        Mood m5 = new Mood("20150589", "前天", R.drawable.img14, "我感觉我的专业课要挂了！");
        mood.add(m5);
        Mood m6 = new Mood("20151252", "刚刚", R.drawable.img15, "我室友今天给我买了一碗凉糕，觉得世界太美好啦！");
        mood.add(m6);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 2) {
            Mood m = new Mood();
            m.setContent(data.getStringExtra("content"));
            m.setName(data.getStringExtra("username"));
            m.setTime(data.getStringExtra("time"));
            mood.add(m);
            listView.setAdapter(adapter);
            listView.setSelection(mood.size() - 1);
            adapter.notifyDataSetChanged();
        }
    }
}

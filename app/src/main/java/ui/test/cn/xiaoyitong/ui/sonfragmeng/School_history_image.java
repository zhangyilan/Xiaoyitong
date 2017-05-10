package ui.test.cn.xiaoyitong.ui.sonfragmeng;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;

import ui.test.cn.xiaoyitong.R;

/**
 * Created by lenovo on 2017/04/25.
 */

public class School_history_image extends Activity {
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.school_history_img);
        imageView= (ImageView) findViewById(R.id.school_history_imagebview);
        Intent intent=getIntent();
        String month=intent.getStringExtra("month");
        if (month.equals("二月")){
            imageView.setImageResource(R.drawable.feb);
        }
        if (month.equals("三月")){
            imageView.setImageResource(R.drawable.mar);
        }
        if (month.equals("四月")){
            imageView.setImageResource(R.drawable.apr);
        }
        if (month.equals("五月")){
            imageView.setImageResource(R.drawable.mm);
        }
        if (month.equals("六月")){
            imageView.setImageResource(R.drawable.jun);
        }

        if (month.equals("七月")){
            imageView.setImageResource(R.drawable.july);
        }
        if (month.equals("八月")){
            imageView.setImageResource(R.drawable.aug);
        }
        if (month.equals("九月")){
            imageView.setImageResource(R.drawable.sep);
        }
        if (month.equals("十月")){
            imageView.setImageResource(R.drawable.oct);
        }
        if (month.equals("十一月")){
            imageView.setImageResource(R.drawable.nov);
        }
        if (month.equals("十二月")){
            imageView.setImageResource(R.drawable.dec);
        }
        if (month.equals("一月")){
            imageView.setImageResource(R.drawable.jan);
        }


    }
}

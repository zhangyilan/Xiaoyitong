package ui.test.cn.xiaoyitong.ui.sonfragmeng;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import ui.test.cn.xiaoyitong.R;
import ui.test.cn.xiaoyitong.adapter.ViewPagerAdapter;

/**
 * Created by asus on 2017/4/19.
 */

public class PersonalActiity extends AppCompatActivity{
    private String tabTitles[] = new String[]{"全部", "待支付", "待收货", "待评论", "已完成"};
    private LinearLayout head_layout;
    private TabLayout toolbar_tab;
    private ViewPager main_vp_container;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private CoordinatorLayout root_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal);
        AppBarLayout app_bar_layout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        head_layout = (LinearLayout) findViewById(R.id.head_layout);
        root_layout = (CoordinatorLayout) findViewById(R.id.root_layout);
        //使用CollapsingToolbarLayout必须把title设置到CollapsingToolbarLayout上，设置到Toolbar上则不会显示
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id
                .collapsing_toolbar_layout);
        app_bar_layout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset <= -head_layout.getHeight() / 2) {
                    mCollapsingToolbarLayout.setTitle("佳佳");
                } else {
                    mCollapsingToolbarLayout.setTitle(" ");
                }
            }
        });

        initviews();
        loadBlurAndSetStatusBar();

        ImageView head_iv = (ImageView) findViewById(R.id.head_iv);
        Glide.with(this).load(R.drawable.bg).bitmapTransform(new RoundedCornersTransformation(this,
                90, 0)).into(head_iv);

    }

    private void initviews() {
        toolbar_tab = (TabLayout) findViewById(R.id.toolbar_tab);
        main_vp_container = (ViewPager) findViewById(R.id.main_vp_container);

        List<Fragment> fragments = new ArrayList<>();
        for (int i = 0; i < tabTitles.length; i++) {
            fragments.add(TabLayoutFragment.newInstance(i));
        }
        ViewPagerAdapter  vpAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments);
        //给ViewPager设置适配器
        main_vp_container.setAdapter(vpAdapter);
        //将TabLayout和ViewPager关联起来。
        toolbar_tab.setupWithViewPager(main_vp_container);
        //设置可以滑动
        toolbar_tab.setTabMode(TabLayout.GRAVITY_CENTER);
    }

    /**
     * 设置毛玻璃效果和沉浸状态栏
     */

    private void loadBlurAndSetStatusBar() {
        StatusBarUtil.setTranslucent(PersonalActiity.this, StatusBarUtil.DEFAULT_STATUS_BAR_ALPHA);
        Glide.with(this).load(R.drawable.bg).bitmapTransform(new BlurTransformation(this, 100))
                .into(new SimpleTarget<GlideDrawable>() {
                    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super
                                                GlideDrawable> glideAnimation) {
                        head_layout.setBackground(resource);
                        root_layout.setBackground(resource);
                    }
                });
        Glide.with(this).load(R.drawable.bg).bitmapTransform(new BlurTransformation(this, 100))
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super
                            GlideDrawable> glideAnimation) {
                        mCollapsingToolbarLayout.setContentScrim(resource);
                    }
                });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String msg = "";
        switch (item.getItemId()) {
            case R.id.webview:
                msg += "博客跳转";
                break;
            case R.id.weibo:
                msg += "微博跳转";
                break;
            case R.id.action_settings:
                msg += "设置";
                break;
        }
        if (!msg.equals("")) {
            Toast.makeText(PersonalActiity.this, msg, Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}

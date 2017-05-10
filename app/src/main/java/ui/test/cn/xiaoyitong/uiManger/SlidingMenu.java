package ui.test.cn.xiaoyitong.uiManger;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.nineoldandroids.view.ViewHelper;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import java.lang.reflect.GenericArrayType;

import ui.test.cn.xiaoyitong.R;

/**
 * Created by asus on 2017/3/31.
 */

public class SlidingMenu extends HorizontalScrollView {
    private Context context;
    private LinearLayout mwapper;
    private ViewGroup mMenu;
    private ViewGroup mcontent;
    private int mScreenWidth;
    private int mMenuWidth;
    //dp
    private int mMenuRightPadding = 80;
    private boolean once = false;
    //设置手势处理 主要用来处理快速滑动
    private GestureDetector mgesture;
    // 菜单是否打开
    private boolean mMenuIsOpen = false;

    public SlidingMenu(Context context) {
        this(context, null);
        this.context = context;
    }

    /**
     * 未使用自定义属性时，调用
     *
     * @param context
     * @param attrs
     */
    public SlidingMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    /**
     * 当使用了自定属性时，会调用这个方法
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public SlidingMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获取我们定义的属性
        mgesture = new GestureDetector(context, new GestureListener());

        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SlidingMenu,
                defStyleAttr, 0);
        //自定义属性数量
        int n = array.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = array.getIndex(i);
            switch (attr) {
                case R.styleable.SlidingMenu_rightPadding:
                    mMenuRightPadding = array.getDimensionPixelSize(attr, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 80, context.getResources()
                            .getDisplayMetrics()));

                    break;
            }
        }
        array.recycle();


        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outmes = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(outmes);
        mScreenWidth = outmes.widthPixels;


        //转化单位==dp转化成px
        mMenuRightPadding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 80, context.getResources()
                .getDisplayMetrics());
    }

    /**
     * 设置子view的宽和高
     * 设置自己的宽和高
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!once) {
            mwapper = (LinearLayout) getChildAt(0);
            mMenu = (ViewGroup) mwapper.getChildAt(0);
            mcontent = (ViewGroup) mwapper.getChildAt(1);
            //子布局宽和高
            mMenuWidth = mMenu.getLayoutParams().width = mScreenWidth - mMenuRightPadding;
            mcontent.getLayoutParams().width = mScreenWidth;
            once = true;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 通过设置偏移量将menu隐藏
     *
     * @param changed
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            //实现menu隐藏
            this.scrollTo(mMenuWidth, 0);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mgesture.onTouchEvent(ev)) {
            return mgesture.onTouchEvent(ev);
        }
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_UP:
                // 隐藏在左边的宽度
                int scrollX = getScrollX();
                if (scrollX >= mMenuWidth / 2) {
                    this.smoothScrollTo(mMenuWidth, 0);

                    mMenuIsOpen = false;
                } else {
                    this.smoothScrollTo(0, 0);
                    mMenuIsOpen = true;
                }
                return true;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 打开菜单
     */
    private void openMenu() {
        if (mMenuIsOpen) return;
        this.smoothScrollTo(0, 0);
        mMenuIsOpen = true;
    }

    /**
     * 关闭菜单
     */
    private void closeMenu() {
        if (!mMenuIsOpen) return;
        this.smoothScrollTo(mMenuWidth, 0);
        mMenuIsOpen = false;
    }

    /**
     * 切换菜单的状态
     */
    private void toggleMenu() {
        if (mMenuIsOpen) {

            closeMenu();
        } else {

            openMenu();
        }
    }

    /**
     * 滚动发生时
     *
     * @param l
     * @param t
     * @param oldl
     * @param oldt
     */
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        //设置动画偏移量，l是内容活动大小，初始化是1
        float scroll = l * 1.0f / mMenuWidth;//从0到1变化
        float leftScale = 1.0f - scroll * 0.3f;

        //设置菜单透明度的变化
        float leftAlpha = 0.6f + 0.4f * (1 - scroll);
        //设置context透明度
        float rightAlpha = 0.8f + 0.2f * scroll;

        //设置属性动画，设置TranslationX
        ViewHelper.setTranslationX(mMenu, mMenuWidth * scroll * 0.7f);
        ViewHelper.setScaleX(mMenu, leftScale);
        ViewHelper.setScaleY(mMenu, leftScale);
        ViewHelper.setAlpha(mMenu, leftAlpha);
        ViewHelper.setAlpha(mcontent, rightAlpha);
//        ViewHelper.setTranslationX(mMenu,l);

    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            // 当手指快速滑动时候回调的方法
            Log.d("TAG", velocityX + "");
            // 如果菜单打开 并且是向左快速滑动 切换菜单的状态
            if (mMenuIsOpen) {
                if (velocityX < -500) {
                    Log.d("TAG", velocityX + "");
                    toggleMenu();
                    return true;
                }
            } else {
                // 如果菜单关闭 并且是向右快速滑动 切换菜单的状态
                if (velocityX > 500) {
                    Log.d("TAG", velocityX + "");
                    toggleMenu();
                    return true;
                }
            }
            return false;
        }
    }


}

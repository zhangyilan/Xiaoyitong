package maplabeing.canvas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.SystemClock;
import android.view.View;

import com.amap.api.maps.model.BitmapDescriptorFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import maplabeing.entity.Parkentity;
import ui.test.cn.xiaoyitong.R;

/**
 * Created by renba on 2017/5/2.
 */

public class CanvasView extends View {
    private int width;
    private int height;
    List<Parkentity> park = new ArrayList<>();
    private int kongwei;
    BitmapDrawable mDrawable;
    /**/
    int carsize = 1;
    private int mViewWidth;
    private int mViewHeight;
    private long mStartX;
    private long mEndX;
    private long mStartY;
    private long mEndY;
    private long mStartTime = -1;
    private long mStartOffset = 1000;
    private long mDuration = 2000;
private String index;
    public CanvasView(Context context, int width, int height, List<Parkentity> listPark, String index) {
        super(context);
        this.width = width;
        this.height = height;
        this.park = listPark;
        this.index=index;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /*N

         * 方法 说明 drawRect 绘制矩形 drawCircle 绘制圆形 drawOval 绘制椭圆 drawPath 绘制任意多边形
         * drawLine 绘制直线 drawPoin 绘制点
         */
        // 创建画笔
        carsize = park.size();
        Paint p = new Paint();
        p.setAntiAlias(true);
        p.setColor(getResources().getColor(R.color.yellow));
        p.setStyle(Paint.Style.FILL);//设置填满
        for (int j = 0; j < park.size(); j++) {
            if (park.get(j).getPlateNumber().equals("空位")) {
                kongwei = j;
                p.setTextSize(50);
                canvas.drawText("博学馆", (width / carsize) * j, (height / carsize) + 210, p);
            }

        }
        for (int i = 0; i < park.size(); i++) {


            if (park.get(i).getIsNull() == 1) {
                if (i < kongwei) {
                    p.setTextSize(30);
                    canvas.drawText(String.valueOf(i + 1), width / carsize, 500 + 100 * i, p);
                    canvas.drawText(park.get(i).getPlateNumber(), (width / carsize) + width / carsize / 2, 500 + 100 * i, p);
                    canvas.drawText(park.get(i).getParkTime(), (width / carsize) * 4, 500 + 100 * i, p);
                    canvas.drawText(String.valueOf(i + 1), (width / carsize) * i + width / carsize / 2, height / carsize + 180, p);
                    Bitmap bitmap = BitmapDescriptorFactory.fromResource(
                            R.drawable.youche).getBitmap();
                    canvas.drawBitmap(bitmap, (width / carsize) * i + 10, 235, p);
                } else if (i > kongwei) {
                    p.setColor(getResources().getColor(R.color.yellow));
                    p.setTextSize(30);
                    canvas.drawText(String.valueOf(i), width / carsize, 500 + 100 * i, p);
                    canvas.drawText(park.get(i).getPlateNumber(), (width / carsize) + width / carsize / 2, 500 + 100 * i, p);
                    canvas.drawText(park.get(i).getParkTime(), (width / carsize) * 4, 500 + 100 * i, p);
                    canvas.drawText(String.valueOf(i), (width / carsize) * i + width / carsize / 2, height / carsize + 180, p);
                    Bitmap bitmap = BitmapDescriptorFactory.fromResource(
                            R.drawable.youche).getBitmap();
                    canvas.drawBitmap(bitmap, (width / carsize) * i + 10, 235, p);
                }


            } else if (park.get(i).getIsNull() == 0) {
                if (i < kongwei) {
                    p.setTextSize(30);
                    p.setColor(getResources().getColor(R.color.blue));
                    canvas.drawText(String.valueOf(i + 1), (width / carsize) * i + width / carsize / 2, height / carsize + 180, p);
                    Bitmap bitmap = BitmapDescriptorFactory.fromResource(
                            R.drawable.wuche).getBitmap();
                    canvas.drawBitmap(bitmap, (width / carsize) * i + 10, 235, p);
                    p.setColor(getResources().getColor(R.color.yellow));
                    canvas.drawText(String.valueOf(i + 1), width / carsize, 500 + 100 * i, p);
                    canvas.drawText("空车位", (width / carsize) + width / carsize / 2, 500 + 100 * i, p);
                } else if (i > kongwei) {
                    p.setColor(getResources().getColor(R.color.blue));

                    canvas.drawText(String.valueOf(i), (width / carsize) * i + width / carsize / 2, height / carsize + 180, p);
                    Bitmap bitmap = BitmapDescriptorFactory.fromResource(
                            R.drawable.wuche).getBitmap();
                    canvas.drawBitmap(bitmap, (width / carsize) * i + 10, 235, p);
                    p.setColor(getResources().getColor(R.color.yellow));
                    canvas.drawText(String.valueOf(i), width / carsize, 500 + 100 * i, p);
                    canvas.drawText("空车位", (width / carsize) + width / carsize / 2, 500 + 100 * i, p);
                }


            } else if (park.get(i).getIsNull() == 2) {
                if (i < kongwei) {
                    p.setColor(getResources().getColor(R.color.black));
                    p.setTextSize(30);
                    canvas.drawText(String.valueOf(i + 1), width / carsize, 500 + 100 * i, p);
                    canvas.drawText(park.get(i).getPlateNumber(), (width / carsize) + width / carsize / 2, 500 + 100 * i, p);
                    canvas.drawText(park.get(i).getParkTime(), (width / carsize) * 4, 500 + 100 * i, p);
                    canvas.drawText(String.valueOf(i + 1), (width / carsize) * i + width / carsize / 2, height / carsize + 180, p);
                    Bitmap bitmap = BitmapDescriptorFactory.fromResource(R.drawable.youche).getBitmap();
                    canvas.drawBitmap(bitmap, (width / carsize) * i + 10, 235, p);
                } else if (i > kongwei) {
                    p.setColor(getResources().getColor(R.color.black));
                    p.setTextSize(30);
                    canvas.drawText(String.valueOf(i), width / carsize, 500 + 100 * i, p);
                    canvas.drawText(park.get(i).getPlateNumber(), (width / carsize) + width / carsize / 2, 500 + 100 * i, p);
                    canvas.drawText(park.get(i).getParkTime(), (width / carsize) * 4, 500 + 100 * i, p);
                    canvas.drawText(String.valueOf(i), (width / carsize) * i + width / carsize / 2, height / carsize + 180, p);
                    Bitmap bitmap = BitmapDescriptorFactory.fromResource(R.drawable.youche).getBitmap();
                    canvas.drawBitmap(bitmap, (width / carsize) * i + 10, 235, p);
                }
            }
        }
        if (mStartTime == -1) {
            mStartTime = SystemClock.uptimeMillis();
        }
        long curTime = SystemClock.uptimeMillis();
        boolean done = true;

        // t为一个0到1均匀变化的值
        float t = (curTime - mStartTime - mStartOffset) / (float) mDuration;
        t = Math.max(0, Math.min(t, 1));
        int translateX = (int) lerp(mStartX, mEndX, t);
        int translateY = (int) lerp(mStartY, mEndY, t);
        if (t < 1) {
            done = false;
        }
        if (0 < t && t <= 1) {
            done = false;
            // 保存画布，方便下次绘制
            canvas.save();
            canvas.translate(translateX, translateY);
            mDrawable.draw(canvas);
            canvas.restore();
        }

        if (!done) {
            invalidate();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mViewWidth = w;
        mViewHeight = h;
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                init();
            }
        };
        timer.schedule(timerTask, 1000);

    }

    private void init() {
        Bitmap bitmap = BitmapDescriptorFactory.fromResource(
                R.drawable.youche).getBitmap();
        mDrawable = new BitmapDrawable(bitmap);
        mDrawable.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());

        mStartX = (mViewWidth / carsize) * (Integer.parseInt(index)-1) + 10;
        mStartY = height - 200;
        mEndX = mStartX;
        mEndY = 235;
    }


    // 数制差
    float lerp(float start, float end, float t) {
        return start + (end - start) * t;
    }
}


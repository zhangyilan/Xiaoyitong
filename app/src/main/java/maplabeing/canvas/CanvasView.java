package maplabeing.canvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import maplabeing.entity.Parkentity;
import ui.test.cn.xiaoyitong.R;

/**
 * Created by renba on 2017/5/2.
 */

public class CanvasView extends View {
    private int width;
    private int height;
    List<Parkentity> park = new ArrayList<>();

    public CanvasView(Context context, int width, int height, List<Parkentity> listPark) {
        super(context);
        this.width = width;
        this.height = height;
        this.park = listPark;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /*N

         * 方法 说明 drawRect 绘制矩形 drawCircle 绘制圆形 drawOval 绘制椭圆 drawPath 绘制任意多边形
         * drawLine 绘制直线 drawPoin 绘制点
         */
        // 创建画笔
        Paint p = new Paint();
        p.setAntiAlias(true);
        p.setColor(getResources().getColor(R.color.yellow));
        p.setStyle(Paint.Style.FILL);//设置填满
        for (int i = 0; i < park.size() ; i++) {
            if (i != 5) {
                if (park.get(i).getIsNull() == 1) {
                    if(i<5){
                        p.setTextSize(30);
                        canvas.drawText(String.valueOf(i + 1),width / 11 ,500+100*i, p);
                        canvas.drawText(park.get(i).getPlateNumber().toString(),(width / 11) + width / 11 / 2, 500+100*i, p);
                        canvas.drawText(park.get(i).getParkTime().toString(),(width / 11)*4, 500+100*i, p);
                        canvas.drawText(String.valueOf(i + 1), (width / 11) * i + width / 11 / 2, height / 11 + 180, p);
                        canvas.drawRect((width / 11) * i + 10, 110 + 50, (width / 11) * i + width / 11, height / 11 + 150, p);
                    }else{
                        p.setColor(getResources().getColor(R.color.yellow));
                        p.setTextSize(30);
                        canvas.drawText(String.valueOf(i),width / 11 ,500+100*i, p);
                        canvas.drawText(park.get(i).getPlateNumber().toString(),(width / 11) + width / 11 / 2, 500+100*i, p);
                        canvas.drawText(park.get(i).getParkTime().toString(),(width / 11)*4, 500+100*i, p);
                        canvas.drawText(String.valueOf(i), (width / 11) * i + width / 11 / 2, height / 11 +180, p);
                      //  canvas.drawRect((width / 11) *( i+1) + 10, 10 + 50, (width / 11) * i + width / 11, height / 11 + 50, p);
                        canvas.drawRect((width / 11) * i + 10, 110 + 50, (width / 11) * i + width / 11, height / 11 + 150, p);
                    }


                } else {
                    if(i<5){
                        p.setTextSize(30);
                        p.setColor(getResources().getColor(R.color.blue));
                        canvas.drawText(String.valueOf(i+1), (width / 11) * i + width / 11 / 2, height / 11 + 180, p);
                        canvas.drawRect((width / 11) * i + 10, 110 + 50, (width / 11) * i + width / 11, height / 11 + 150, p);
                        p.setColor(getResources().getColor(R.color.yellow));
                        canvas.drawText(String.valueOf(i+1),width / 11 ,500+100*i, p);
                        canvas.drawText("空车位",(width / 11) + width / 11 / 2, 500+100*i, p);
                    }else {
                        p.setColor(getResources().getColor(R.color.blue));

                        canvas.drawText(String.valueOf(i), (width / 11) * i + width / 11 / 2, height / 11 + 180, p);
                        canvas.drawRect((width / 11) * i + 10, 110 + 50, (width / 11) * i + width / 11, height / 11 + 150, p);
                        p.setColor(getResources().getColor(R.color.yellow));
                        canvas.drawText(String.valueOf(i ),width / 11 ,500+100*i, p);
                        canvas.drawText("空车位",(width / 11) + width / 11 / 2, 500+100* i, p);
                    }
                }
            } else {
                p.setTextSize(50);
                canvas.drawText("博学馆", (width / 11) * i, (height / 11) + 210, p);


            }
        }
    }


}


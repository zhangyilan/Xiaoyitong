package maplabeing.util;

import android.app.Activity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class StatusBarLightMode {

	public static void StatusBarLightMode(Activity activity, int type){
	    if(type==1){
	       MIUISetStatusBarLightMode(activity.getWindow(), true);
	    }else if(type==2){
	        FlymeSetStatusBarLightMode(activity.getWindow(), true);
	    }else if(type==3){
	        activity.getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
	    }

	}
	public static boolean FlymeSetStatusBarLightMode(Window window, boolean dark) {
	    boolean result = false;
	    if (window != null) {
	        try {
	            WindowManager.LayoutParams lp = window.getAttributes();
	            Field darkFlag = WindowManager.LayoutParams.class
	                    .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
	            Field meizuFlags = WindowManager.LayoutParams.class
	                    .getDeclaredField("meizuFlags");
	            darkFlag.setAccessible(true);
	            meizuFlags.setAccessible(true);
	            int bit = darkFlag.getInt(null);
	            int value = meizuFlags.getInt(lp);
	            if (dark) {
	                value |= bit;
	            } else {
	                value &= ~bit;
	            }
	            meizuFlags.setInt(lp, value);
	            window.setAttributes(lp);
	            result = true;
	        } catch (Exception ignored) {

	        }
	    }
	    return result;
	}
	private static boolean MIUISetStatusBarLightMode(Window window, boolean dark) {
	    boolean result = false;
	    if (window != null) {
	        Class clazz = window.getClass();
	        try {
	            int darkModeFlag;
	            Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
	            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
	            darkModeFlag = field.getInt(layoutParams);
	            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
	            if(dark){
	                extraFlagField.invoke(window,darkModeFlag,darkModeFlag);//״̬��͸���Һ�ɫ����
	            }else{
	                extraFlagField.invoke(window, 0, darkModeFlag);//�����ɫ����
	            }
	            result=true;
	        }catch (Exception e){

	        }
	    }
	    return result;
	}

}
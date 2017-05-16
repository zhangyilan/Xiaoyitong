package ui.test.cn.xiaoyitong.entity;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;


public final class DateUtils {
	
	/**
	 * 根据给出的格式化方式,将时间转换为字符串表示
	 * 
	 * @param date
	 * @param formater
	 * @return
	 */
	@SuppressLint("SimpleDateFormat")
	public static String formaterDate(Date date, String formater) {
		if (date == null) {
			return "";
		}
		if (formater == null) {
			formater = "yyyy-MM-dd";
		}
		return new SimpleDateFormat(formater).format(date);
	}
	
	/**
	 * 将时间转换为年月日小时分钟的格式
	 * 
	 * @param date
	 * @return
	 */
	public static String formaterDate2YMDHm(Date date) {
		return formaterDate(date, "yyyy-MM-dd HH:mm");
	}	
}

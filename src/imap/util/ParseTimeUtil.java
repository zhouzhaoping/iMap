package imap.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 *  
 *
 */
public class ParseTimeUtil {
	public static String format(long ms) {
		int ss = 1000;
		int mi = ss * 60;
		int hh = mi * 60;
		int dd = hh * 24;

		long day = ms / dd;
		long hour = (ms - day * dd) / hh;
		long minute = (ms - day * dd - hour * hh) / mi;
		long second = (ms - day * dd - hour * hh - minute * mi) / ss;
		long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

		String strDay = day < 10 ? "0" + day : "" + day;
		String strHour = hour < 10 ? "0" + hour : "" + hour;
		String strMinute = minute < 10 ? "0" + minute : "" + minute;
		String strSecond = second < 10 ? "0" + second : "" + second;
		String strMilliSecond = milliSecond < 10 ? "0" + milliSecond : "" + milliSecond;
		strMilliSecond = milliSecond < 100 ? "0" + strMilliSecond : "" + strMilliSecond;
		return strMinute + ":" + strSecond ;

		}
	
	//"yyyy-MM-dd HH:mm:ss" 与当前时间的时间差
	public static String getTimeDelta(String str, String f) {
		
		Calendar now = Calendar.getInstance(); 
		Date nowdata = new Date();
		// 默认当前系统时间是对的
		SimpleDateFormat sdf = new SimpleDateFormat(f);
		Date d;
		try {
			d = sdf.parse(str);
			if (now.get(Calendar.YEAR) != d.getYear() + 1900)
				return (d.getYear() + 1900) + "年" + d.getMonth() + "月";
			if (now.get(Calendar.MONTH) != d.getMonth())
				return (now.get(Calendar.MONTH) - d.getMonth()) + "月前";
			if (now.get(Calendar.DATE) != d.getDate())
				return (now.get(Calendar.DATE) - d.getDate()) + "天前";
			if (now.get(Calendar.HOUR_OF_DAY) != d.getHours())
				return (now.get(Calendar.HOUR_OF_DAY) + 1 - d.getHours()) + "小时前";
			if (now.get(Calendar.MINUTE) != d.getMinutes())
				return (now.get(Calendar.MINUTE) + 1 - d.getMinutes()) + "分钟前";
			if (now.get(Calendar.SECOND) != d.getSeconds())
				return (now.get(Calendar.SECOND) + 1 - d.getSeconds()) + "秒前";
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "不明时间";
	}
}

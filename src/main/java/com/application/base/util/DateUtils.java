package com.application.base.util;

import org.apache.commons.lang3.StringUtils;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;


/**
 * @author : 孤狼
 * @NAME: DateUtils
 * @DESC: DateUtils
 **/
public class DateUtils {
	
	/**
	 * 年 - 月 - 日
	 */
	public int year=0, month=0, day=0;
	
	/**
	 * 时间函数
	 */
	public static final String YMD_HMS_PATTERN = "yyyy-MM-dd HH:mm:ss";
	
	public static final String YMD_PATTERN = "yyyy-MM-dd";

	/**
	 * @description 获得当前日期不带时分秒
	 * 
	 * @date 
	 */
	public static String getNowDate() {
		return getSampleDate(new Date());
	}

	/**
	 * @description 获得当前日期带时分秒
	 * 
	 * @date 
	 */
	public static String getNowDateTime() {
		return getDateTime(new Date());
	}

	/**
	 * @description 获得当前日期时间戳
	 * 
	 * @date 2018年3月21日
	 * @return
	 */
	public static String getNowTimestamp() {
		return getTimestamp(new Date());
	}

	/**
	 * @description 获得当前日期的long类型时间戳
	 * 
	 * @date 2018年4月26日
	 * @return
	 */
	public static long getNowLongTime() {
		return getLongTime(new Date());
	}

	/**
	 * @description 字符串整型日期转换为日期
	 * 
	 * @date 2018年12月24日
	 * @param longDate
	 * @return
	 */
	public static Date long2Date(String longDate) {
		if (!StringUtils.isEmpty(longDate)) {
			long date = Long.parseLong(longDate);
			return long2Date(date);
		}
		return null;
	}

	/**
	 * @description long转换为日期
	 * 
	 * @date 2018年12月24日
	 * @param longDate
	 * @return
	 */
	public static Date long2Date(long longDate) {
		return new Date(longDate);
	}

	/**
	 * @description long整型日期转换为指定格式日期
	 * 
	 * @date 2018年12月24日
	 * @param date
	 * @param format
	 * @return
	 */
	public static String getFormatDate(long longDate, String format) {
		return getDate(long2Date(longDate), format);
	}
	/**
	 * @description 字符串整型日期转换为指定格式日期
	 * 
	 * @date 2018年12月24日
	 * @param longDate
	 * @param format
	 * @return
	 */
	public static String getFormatDate(String longDate, String format) {
		return getDate(long2Date(longDate), format);
	}
	/**
	 * @description long整型日期转换为yyyy-MM-dd HH:mm:ss
	 * 
	 * @return
	 */
	public static String getDateTime(long longDate) {
		return getDateTime(long2Date(longDate));
	}
	/**
	 * @description 字符串整型日期转换为yyyy-MM-dd HH:mm:ss
	 * 
	 * @date 2018年12月24日
	 * @param longDate
	 * @return
	 */
	public static String getDateTime(String longDate) {
		return getDateTime(long2Date(longDate));
	}
	/**
	 * @description 获得yyyy-MM-dd HH:mm:ss格式的日期
	 * 
	 * @date 
	 */
	public static String getDateTime(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(YMD_HMS_PATTERN);
		return sdf.format(date);
	}

	/**
	 * @description 获得yyyy-MM-dd格式的日期
	 * 
	 * @date 
	 */
	public static String getSampleDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(YMD_PATTERN);
		return sdf.format(date);
	}

	/**
	 * 获取当前日期 以XXXX年XX月XX日格式返回
	 *
	 * @return Date
	 */
	public static String currentDateTime() {
		return DateUtils.getYear() + "年" + DateUtils.getMonth() + "月"
				+ DateUtils.getDay() + "日";
	}

	/**
	 * @description 获得指定时间的时间戳
	 * 
	 * @date 2018年3月21日
	 * @param date
	 * @return
	 */
	public static String getTimestamp(Date date) {
		return String.valueOf(date.getTime());
	}
	/**
	 * @description 获得指定时间的long类型时间戳
	 * 
	 * @date 2018年4月26日
	 * @param date
	 * @return
	 */
	public static long getLongTime(Date date) {
		return date.getTime();
	}
	/**
	 * @description 获得指定字符串时间的long类型时间戳
	 * 
	 * @date 2018年5月3日
	 * @param date
	 * @return
	 */
	public static long getLongTime(String date) {
		long longTime = 0;
		try {
			longTime = getFormat(date).getTime();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return longTime;
	}
	/**
	 * @description 按照自定义格式格式化日期
	 * 
	 * @date 2018年3月21日
	 * @param date
	 * @param format
	 * @return
	 */
	public static String getDate(Date date, String format) {
		SimpleDateFormat dateformat = new SimpleDateFormat(format);
		return dateformat.format(date);
	}

	/**
	 * @description 按照自定义格式获取当前日期字符串
	 * 
	 * @date 2018年3月26日
	 * @param format
	 * @return
	 */
	public static String getDate(String format) {
		SimpleDateFormat dateformat = new SimpleDateFormat(format);
		return dateformat.format(new Date());
	}




	/**
	 * @description
	 * 
	 * @date 2018年3月21日
	 * @param nowTime
	 * @param days
	 * @return
	 */
	public static long getNowDateOfDays(long nowTime, int days) {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date(nowTime));
		c.add(Calendar.DAY_OF_YEAR, days);
		return c.getTime().getTime();
	}

	/**
	 * @description
	 * 
	 * @date 2018年3月21日
	 * @return
	 */
	public static String getDateToString() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
		return sdf.format(date).substring(3);
	}

	/**
	 * @description 日期
	 * 
	 * @date 
	 */
	public static String dateToString(Date date, String pattern) {
		if (date != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			return sdf.format(date);
		}
		return "";
	}


	public static String[] sss = new String[] { "EEE MMM dd HH:mm:ss zzz yyyy", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "MM/dd/yyyy HH:mm:ss", "yyyy-MM-dd HH", "yyyy-MM-dd", "yyMMddHHmmss" };
	
	/**
	 * @description 格式化不同的日期格式
	 * 
	 * @date 
	 */
	public static Date getFormat(String date) throws Exception {
		if (date == null || date.equalsIgnoreCase(""))
			return null;
		for (int i = 0; i < sss.length; i++) {
			SimpleDateFormat sdf = new SimpleDateFormat(sss[i], Locale.US);
			try {
				return sdf.parse(date);
			} catch (ParseException e) {
			}

		}
		throw new Exception();
	}


	/**
	 * @description 格式化日期为字符串
	 * 
	 * @date 
	 */
	public static String formatDateByPattern(Date date, String dateFormat) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		String formatTimeStr = null;
		if (date != null) {
			formatTimeStr = sdf.format(date);
		}
		return formatTimeStr;
	}

	/**
	 * @description 特殊格式日期 (ss mm HH dd MM ? yyyy)
	 * 
	 * @date 
	 */
	public static String formatDateByPattern(String date, String dateFormat) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		SimpleDateFormat dateformat = new SimpleDateFormat(YMD_HMS_PATTERN);
		if(StringUtils.isNotEmpty(date)){
			try {
				return dateformat.format(sdf.parse(date));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 得到当前年份
	 *
	 * @return  返回当前年份
	 *
	 */
	public static String getYear() {
		Calendar calendar=Calendar.getInstance();
		return String.valueOf(calendar.get(Calendar.YEAR));
	}

	/**
	 * 得到过去N年的年份
	 *
	 * @return  返回当前年份N年前的年份
	 *
	 */
	public static String getLastNumYear(int num) {
		Calendar calendar=Calendar.getInstance();
		return String.valueOf(calendar.get(Calendar.YEAR) - num);
	}

	/**
	 * 得到当前月份
	 *
	 * @return  返回当前月份
	 *
	 */
	public static String getMonth() {
		Calendar calendar=Calendar.getInstance();
		return String.valueOf(calendar.get(Calendar.MONTH)+1);
	}
	/**
	 * 得到当前月份
	 * ex:2018-05-16 12:24:34 -->05
	 * @return
	 */

	public static String getMonth1() {
		 String dateTime=DateUtils.dateToString(new Date(), "yyy-MM-dd hh:mm:ss");
		 return dateTime.substring(5, 7);
	}

	/**
	 * @description 获得当天
	 * 
	 * @date 
	 */
	public static String getDay() {
		return getNowDate().substring(8, 10);
	}


	/**
	 * 得到1970 年 1 月 1 日 00:00:00 GMT 以来此 Date 对象表示的毫秒数
	 * @return 返回自 1970 年 1 月 1 日 00:00:00 GMT 以来此 Date 对象表示的毫秒数
	 */

	public static String getTime() {

		Date date = new Date();
		Time time;
		time = new Time(date.getTime());
		String strTime = time.toString();

		return strTime;
	}

	/**
	 *
	 *  一天中的小时（24小时制）
	 * @return 返回一天中的小时
	 *
	 */
	public static String getHour() {
		Calendar calendar=Calendar.getInstance();
		return String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));

	}

	/**
	*
	*  一小时中的分钟
	* @return 返回一小时中的分钟
	*
	*/
	public static String getMinute() {
		Calendar calendar=Calendar.getInstance();
		return String.valueOf(calendar.get(Calendar.MINUTE));
	}


	/**
	 *
	 *  一分钟中的秒
	 * @return 返回一分钟中的秒
	 *
	 */
	public static String getSecond() {
		Calendar calendar=Calendar.getInstance();
		return String.valueOf(calendar.get(Calendar.SECOND));
	}

	/**
	 *
	 * 得到指定月的上一个月的月份
	 * @param 月份
	 * @return 返回指定月的上一个月的月份
	 *
	 */
	public static String getUpMonth(String month) {

		try{
			int i = new Integer(month).intValue();

			if (i > 1)
				i--;
			else
				i = 12;
			return String.valueOf(i);

		} catch (Exception e) {
			return "err";
		}
	}

	/**
	 *
	 * 得到指定年月的上一个月的年份
	 * @param 年份
	 * @param 月份
	 * @return 返回指定年月的上一个月的年份
	 *
	 */
	public static String getUpYear(String year,String month) {

		try{
			int i = new Integer(month).intValue();

			int getyear=new Integer(year).intValue();

			if (i==1)
			{
				getyear--;
			}

			return String.valueOf(getyear);

		} catch (Exception e) {
			return "err";
		}
	}

	/**
	 *
	 * 得到当前月的下一个月的月份
	 * @return 返回当前月的下一个月的月份
	 *
	 */
	public static String getDownMonth() {
		int i = new Integer(getMonth()).intValue();
		if (i ==12)
			i = 1;
		else
			i++;
		return String.valueOf(i);
	}

	/**
	 *
	 *  得到指定月的下一个月的月份
	 * @param 月份
	 * @return 返回指定月的下一个月的月份
	 *
	 */
	public static String getDownMonth(String month) {
		try{
			int i = new Integer(month).intValue();
			if (i == 12)
				i = 1;
			else
				i++;
			return String.valueOf(i);
		} catch (Exception e) {
			return "err";
		}
	}

	/**
	 *
	 * 得到指定年月的下一个月的年份
	 * @param 年份
	 * @param 月份
	 * @return 返回指定年月的下一个月的年份
	 *
	 */
	public static String getDownYear(String year,String month) {

		try{
			int i = new Integer(month).intValue();

			int getyear=new Integer(year).intValue();

			if (i==12)
			{
				getyear++;
			}

			return String.valueOf(getyear);

		} catch (Exception e) {
			return "err";
		}
	}


	/**
	 *
	 * 得到指定日期 相差天数的新日期
	 *
	 * @param   int    相差的天数
	 * @return  String 新日期
	 *
	 */

	public static String RelativeDate(String sourceDate, int Days) {

	   SimpleDateFormat d = new SimpleDateFormat();
			d.applyPattern("yyyy-MM-dd");

		GregorianCalendar cal = new GregorianCalendar();

		java.sql.Date date = null;
		date = java.sql.Date.valueOf(sourceDate);

		cal.setTime(date);
		cal.add(GregorianCalendar.DATE, Days);

		return d.format(cal.getTime());
	}

	/**
	 *
	 * 得到当前日期 相差天数的新日期
	 *
	 * @param   int    相差的天数
	 * @return  String 新日期
	 *
	 */

	public static String RelativeDate(int Days) {

	   SimpleDateFormat d = new SimpleDateFormat();
			d.applyPattern("yyyy-MM-dd");

		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(new Date());
		cal.add(GregorianCalendar.DATE, Days);

		return d.format(cal.getTime());
	}

	/**
	 *
	 * 得到当前日期 相差天数的新日期
	 *
	 */
	public static Date getRelativeDate(int Days) {
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(new Date());
		cal.add(GregorianCalendar.DATE, Days);
		cal.set(GregorianCalendar.HOUR_OF_DAY, 23);
		cal.set(GregorianCalendar.MINUTE, 59);
		cal.set(GregorianCalendar.SECOND, 59);
		cal.set(GregorianCalendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * Description: 得到当前日期相差天数的新日期（Timestamp）
	 * @param days
	 * @return
	 */
	public static Timestamp relativeDate(int days) {
	    SimpleDateFormat d = new SimpleDateFormat();
        d.applyPattern("yyyy-MM-dd HH:mm:ss");

    GregorianCalendar cal = new GregorianCalendar();
    cal.setTime(new Date());
    cal.add(GregorianCalendar.DATE, days);

    return new Timestamp(cal.getTime().getTime());
	}
	/**
	 *
	 * 得到同一年内2个日期之间的相差天数
	 *
	 * @param   String firstDate  (必须为 yyyy-mm-dd 形式)
	 * @param   String secondDate (必须为 yyyy-mm-dd 形式)
	 * @return  String 返回同一年内2个日期之间的相差天数
	 *
	 */

	public static String getDatesChange(String firstDate,String secondDate) {

		String sign="";
		int firstDays=0,secondDays=0;
		Date dateOne=null,dateTwo=null;


	   SimpleDateFormat d = new SimpleDateFormat();
			d.applyPattern("yyyy-MM-dd");
		try {
			GregorianCalendar cal = new GregorianCalendar();

			dateOne=d.parse(firstDate);
			cal.setTime(dateOne);
			firstDays=cal.get(GregorianCalendar.DAY_OF_YEAR);


			dateTwo=d.parse(secondDate);
			cal.setTime(dateTwo);
			secondDays=cal.get(GregorianCalendar.DAY_OF_YEAR);

			sign=String.valueOf(StrictMath.abs(firstDays-secondDays));

		} catch (ParseException e) {
			sign="err";
			e.printStackTrace();
		}

		return  sign;
	}

	/**
	 * @description 两个dataTime的时间差(小时级)
	 * 
	 * @date 2018年4月25日
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static String relativeTime(String startTime, String endTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String result = "";
		try {
			Date start = sdf.parse(startTime);
			Date end = sdf.parse(endTime);
			long temp = end.getTime() - start.getTime();
			if (temp >= 0) {
				result = String.valueOf(temp/1000/60/60);
			}
		} catch (ParseException e) {
			return "";
		}
		return result;
	}
	/**
	 * @description 两个dataTime的时间差(秒级)
	 * 
	 * @date 2018年4月25日
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static String relativeDateTime(String startTime, String endTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String result = "";
		try {
			Date start = sdf.parse(startTime);
			Date end = sdf.parse(endTime);
			long temp = end.getTime() - start.getTime();
			result = String.valueOf(temp/1000);
		} catch (ParseException e) {
			return "";
		}
		return result;
	}
	/**
	 * @description 结束时间跟当前时间的时间差(秒级)
	 * 
	 * @date 2018年4月25日
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static String relativeDateTime(String endTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String result = "";
		try {
			Date start = sdf.parse(getNowDateTime());
			Date end = sdf.parse(endTime);
			long temp = end.getTime() - start.getTime();
			result = String.valueOf(temp/1000);
		} catch (ParseException e) {
			return "";
		}
		return result;
	}

	/**
	 * @description 跟当前时间相比
	 * 
	 * @date 2018年4月25日
	 * @param endTime
	 * @return 返回值为true说明失效，反之没有失效
	 */
	public static boolean isinvalidTime(String time, String invalidTime) {
		boolean flag = false;
		String result = relativeDateTime(time, invalidTime);
		if(StringUtils.isNotEmpty(result)) {
			if (Long.parseLong(result) <= 0) {
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * @description 跟当前时间相比
	 * 
	 * @date 2018年4月25日
	 * @param endTime
	 * @return 返回值为true说明失效，反之没有失效
	 */
	public static boolean isinvalidTime(String invalidTime) {
		boolean flag = false;
		String result = relativeDateTime(getNowDateTime(), invalidTime);
		if(StringUtils.isNotEmpty(result)) {
			if (Long.parseLong(result) <= 0) {
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * 数据类型校验
	 *
	 * @return  boolean 校验后的结果
	 *          true--正确 false--错误
	 */

	public static boolean isDate(String str) {
		try {
			if ((str == null) || (str.equals(""))) {
				return false;

			} else {
				str = str.trim();
			}

			Date date = java.sql.Date.valueOf(str);
			if (!str.equals(date.toString())) {
				return false;
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}


	/**
	 * 将字符串转换为时间.
	 *
	 * @param str 代表时间的字符串.
	 * @return --转换后的时间.str格式不对返回null.
	 *
	 */

	public static final Timestamp stringToTime(String str) {
		if (str == null)
			return null;
		str = str.trim();
		SimpleDateFormat formatter =
			new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Date tDate = null;
		try {
			tDate = formatter.parse(str);
		} catch (Exception e) {
		}
		if (tDate == null && str.indexOf(' ') < 0) {
			formatter = new SimpleDateFormat("yyyy-MM-dd");
			try {
				tDate = formatter.parse(str);
			} catch (Exception e) {
			}
		}
		if (tDate == null)
			return null;
		Timestamp timeStame = new Timestamp(tDate.getTime());
		return timeStame;
	}
	/**
	 * 将字符串转换为时间.
	 *
	 * @param str 代表时间的字符串.
	 * @return --转换后的时间.str格式不对返回null.
	 *
	 */

	public static final Timestamp stringToTime8(String str) {
		if (str == null)
			return null;
		str = str.trim();
		SimpleDateFormat formatter =
			new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		formatter.setTimeZone(TimeZone.getTimeZone("ETC/GMT+0"));
		Date tDate = null;
		try {
			tDate = formatter.parse(str);
		} catch (Exception e) {
		}
		if (tDate == null && str.indexOf(' ') < 0) {
			formatter = new SimpleDateFormat("yyyy-MM-dd");
			try {
				tDate = formatter.parse(str);
			} catch (Exception e) {
			}
		}
		if (tDate == null)
			return null;
		Timestamp timeStame = new Timestamp(tDate.getTime());
		return timeStame;
	}

	/**
	 * 检查日期的相关值合法性.(三个之中有空值则不校验)
	 *
	 * @param (String)year  年 4位
	 * @param (String)month 月 2位
	 * @param (String)day   日 2位.
	 *
	 * @return (boolean)  true 合法  false 不合法.
	 *
	 */
	public static final boolean isDate(String year, String month, String day) {
		if (year.trim().length() == 0
			|| month.trim().length() == 0
			|| day.trim().length() == 0)
			return true;
		int y, m, d;
		try {
			y = Integer.parseInt(year);
			m = Integer.parseInt(month);
			d = Integer.parseInt(day);
		} catch (NumberFormatException e) {
			return false;
		}
		return isCalender(y, m, d);
	}

	/**
	 * 检查日期的相关值合法性.(三个之中有空值则不校验)
	 *
	 * @param (String)year 年 4位 (String)month (String)day.
	 * @param (允许最大日期) "nnnn-yy-mm" ,"0000-00-00" 不大于当前日期，"" 不校验
	 * @param (允许最小日期) "nnnn-yy-mm" ,"" 不校验
	 *
	 * @return (boolean)  true 合法  false 不合法.
	 *
	 */
	public static final boolean isDate(
		String year,
		String month,
		String day,
		String maxdate,
		String mindate) {
		if (!isDate(year, month, day)
			|| (!DateUtils.isDate(maxdate)
				&& !maxdate.equals("0000-00-00")
				&& !maxdate.trim().equals(""))
			|| (!DateUtils.isDate(mindate) && !mindate.trim().equals("")))
			return false;

		if (maxdate.equals("0000-00-00"))
			return (
				stringToTime(year + "-" + month + "-" + "01").before(
					new Date()));
		if (!maxdate.trim().equals(""))
			return (
				stringToTime(year + "-" + month + "-" + "01").before(
					stringToTime(maxdate)));
		if (!mindate.trim().equals(""))
			return (
				stringToTime(year + "-" + month + "-" + "01").after(
					stringToTime(mindate)));

		return true;
	}

	/**
	 * 检查时间的相关值合法性.
	 *
	 * @param (String)hour (String)minut (String)sec.
	 * @return (boolean)  true 合法  false 不合法.
	 *
	 */

	public static final boolean isTime(
		String hour,
		String minute,
		String secs) {
		int h, m, s;
		try {
			h = Integer.parseInt(hour);
			m = Integer.parseInt(minute);
			s = Integer.parseInt(secs);
		} catch (NumberFormatException e) {
			return false;
		}
		if (h < 0 || h > 23)
			return false;
		if (m < 0 || m > 59)
			return false;
		if (s < 0 || s > 59)
			return false;
		return true;
	}
	/**
	 * 根据输入的字符串解析出年月日和时分秒，并检查日期的有效性。
	 *
	 * @param  (String)  textStr --输入的字符串
	 * @return (boolean) true--成功   false--失败
	 *
	 */
	public static final boolean dateTextFieldParse(String textStr) {
		int index = textStr.indexOf(".");
		if (index > -1)
			textStr = textStr.substring(0, index);
		String year, month, day, hour, minute, second;
		int hypen1Index, hypen2Index, blankIndex, colon1Index, colon2Index;
		hypen1Index = textStr.indexOf("-");
		if (hypen1Index != -1) {
			hypen2Index = textStr.indexOf("-", hypen1Index + 1);
			if (hypen2Index != -1) {
				blankIndex = textStr.indexOf(" ");
				if (blankIndex != -1) {
					colon1Index = textStr.indexOf(":");
					if (colon1Index != -1) {
						colon2Index = textStr.indexOf(":", colon1Index + 1);
						if (colon2Index != -1) {
							//提取年月日和时分秒
							year = textStr.substring(0, hypen1Index);
							month =
								textStr.substring(hypen1Index + 1, hypen2Index);
							day =
								textStr.substring(hypen2Index + 1, blankIndex);
							hour =
								textStr.substring(blankIndex + 1, colon1Index);
							minute =
								textStr.substring(colon1Index + 1, colon2Index);
							second = textStr.substring(colon2Index + 1);
							if (isDate(year, month, day)) {
								if (isTime(hour, minute, second))
									return true;
								else
									return false;
							} else
								return false;
						} else
							return false;
					} else
						return false;
				} else //年月日
					{
					year = textStr.substring(0, hypen1Index);
					month = textStr.substring(hypen1Index + 1, hypen2Index);
					day = textStr.substring(hypen2Index + 1);
					if (isDate(year, month, day))
						return true;
					else
						return false;
				}
			} else
				return false;
		} else
			return false;
	}
	public static String getDateErrorMessage() {
		return ("\n  正确格式是：\n  (1) 年-月-日\n  (2) 年-月-日 一个空格 时(24小时制):分:秒\n  (3)年介于1900至4712之间\n   例如：2000-9-8 14:30:30");
	}

	/**
	 * 将传入的日期字符串表达式格式化
	 *
	 */
	public static String tostanddate(String str) {
		str = str.trim();
		if (str.substring(6, 7).equals("-")) {
			str = str.substring(0, 5) + "0" + str.substring(5, str.length());
		}
		if (str.trim().length() < 10) {
			str = str.substring(0, 8) + "0" + str.substring(8, 9);
		}
		return str;
	}
	/**
	 * 将时间字符串增加月份(必须为 yyyy-mm-dd 形式).
	 *
	 * @param str 表示时间的字符串.
	 * @param movemonth 增加的月份   负值为减少月份.
	 * @return --转换后的时间字符串.str格式不对返回"".
	 *
	 */

	public static final String stringMoveMonth(String str, int movemonth) {
		if (!DateUtils.isDate(str))
			return "";
		int year = Integer.parseInt(str.substring(0, 4));
		int month = Integer.parseInt(str.substring(5, 7));
		int moveyear = Math.abs(movemonth);
		moveyear = (int) (moveyear / 12);
		if (movemonth > 0)
			year += moveyear;
		else
			year -= moveyear;

		int passmonth =
			Math.abs(movemonth) - 12 * (int) ((Math.abs(movemonth)) / 12);

		if (passmonth > 0) {
			if (movemonth > 0) {
				if ((movemonth + month) > 12) {
					year++;
					month = movemonth + month - 12;
				} else
					month = movemonth + month;
			} else {
				if (month > Math.abs(movemonth))
					month -= Math.abs(movemonth);
				else {
					year--;
					month = 12 + month + movemonth;
				}
			}
		}
		if (month > 9)
			return String.valueOf(year)
				+ str.substring(4, 5)
				+ String.valueOf(month)
				+ str.substring(7);
		else
			return String.valueOf(year)
				+ str.substring(4, 5)
				+ "0"
				+ String.valueOf(month)
				+ str.substring(7);
	}

	/**
	 * 将当前时间字符串增加月份(必须为 yyyy-mm-dd 形式).
	 *
	 * @param movemonth 增加的月份   负值为减少月份.
	 * @return --转换后的时间字符串.str格式不对返回"".
	 *
	 */

	public static final String stringMoveMonth(int movemonth) {
		return stringMoveMonth(DateUtils.getNowDate(), movemonth);
	}

//	检查年月日是否合法
	  private static boolean isCalender(int y, int m, int d) {
		  //时间值越界；
		  if ((y < 1900) || (y > 4712)) {
			  return false;
		  }
		  if ((m < 1) || (m > 12)) {
			  return false;
		  }
		  if ((d < 1) || (d > 31)) {
			  return false;
		  }
		  //判断天是否正确
		  switch (m) {
			  case 2 :
				  if (isLeapyear(y)) {
					  if (d > 29)
						  return false;
				  } else {
					  if (d > 28)
						  return false;
				  };
				  break;
			  case 4 :
			  case 6 :
			  case 9 :
			  case 11 :
				  if (d > 30)
					  return false;
				  break;
			  default :
				  return true;
		  }
		  return true;
	  }

	/**
	 * 判断给定的年份是否为闰年
	 *
	 * @param 年份
	 * @return 闰年 返回 true 否则返回 false
	 *
	 */
	  private static boolean isLeapyear(int y) {
		  if (((y % 4 == 0) && (y % 100 != 0)) || (y % 400 == 0))
			  return true; //是闰年;
		  else
			  return false;
	  }
	/**
	  * 得到当前月份的最后一天.
	  *
	  * @param  (String)  oldday.
	  * @return (String)  lastday.
	  *
	  */
	public static final String getNowLastDay() {
		return getLastDay(getNowDate());
	}
	/**
		 * 得到某个月份的最后一天.
		 *
		 * @param  (String)  oldday.
		 * @return (String)  lastday.
		 *
		 */

		public static final String getLastDay(String oldday) {

			oldday = tostanddate(oldday);
			if (!isDate(oldday))
				return "error";

			int y = Integer.parseInt(oldday.substring(0, 4));
			int m = Integer.parseInt(oldday.substring(5, 7));
			int d = Integer.parseInt(oldday.substring(8, 10));

			switch (m) {
				case 2 :
					if (isLeap(y)) {
						d = 29;
					} else {
						d = 28;
					};
					break;
				case 4 :
				case 6 :
				case 9 :
				case 11 :
					d = 30;
					break;
				default :
					d = 31;
			}
			return oldday.substring(0, 8) + String.valueOf(d);
		}

		/**
		 * 取当前月的最后一天
		 * @return
		 */
		public static final String getLastDayByNowMonth() {

			String oldday = tostanddate(getNowDate());
			if (!isDate(oldday))
				return "error";

			int y = Integer.parseInt(oldday.substring(0, 4));
			int m = Integer.parseInt(oldday.substring(5, 7));
			int d = Integer.parseInt(oldday.substring(8, 10));

			switch (m) {
				case 2 :
					if (isLeap(y)) {
						d = 29;
					} else {
						d = 28;
					};
					break;
				case 4 :
				case 6 :
				case 9 :
				case 11 :
					d = 30;
					break;
				default :
					d = 31;
			}
			return String.valueOf(d);
		}

	/*
	 *  判断是否闰年?
	 *
	 */
	private final static boolean isLeap(int y) {
		if (((y % 4 == 0) && (y % 100 != 0)) || (y % 400 == 0))
			return true; //是闰年;
		else
			return false;
	}
	/**
		 * 字符串补位
		 *
		 * @param  (int) weishu      －补位后的总位数
		 * @param  (String)num       --需补位的字符串
		 * @param  (String)addchar   --补位字符 (一位字符 空格为 " ")
		 * @param  (int) addloc      --补位位置(1..前补位  2..后补位)
		 * @return (String)          --补位后的字符串
		 *
		 */
		public static final String ForAddWord(
			int weishu,
			String num,
			String addchar,
			int addloc) {
			return ForAddWord(weishu, num, addchar, addloc, 0);
		}
		public static final String ForAddWord(
			int weishu,
			String num,
			String addchar,
			int addloc,
			int mode) {
			if (num.length() >= weishu)
				return num;
			int i = 0;
			int j = weishu - num.length();

			String BH = "";
			while (i < j) {
				BH += addchar;
				i++;
			}
			if (addloc < 2)
				return BH + num;
			else
				return num + BH;
		}

		/**
		 * 获得大写周几
		 * @return
		 */
		public static final String getWeekOfDate(){
			String[] weekDays = {"日", "一", "二", "三", "四", "五", "六"};
	        Calendar cal = Calendar.getInstance();
	        Date nowdate = new Date();
	        cal.setTime(nowdate);

	        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
	        if (w < 0)
	            w = 0;

	        return weekDays[w];
		}
		/**
		 * 获得数字周几
		 * @return
		 */
		public static final String getWeekOfDate2(){
			Calendar cal = Calendar.getInstance();
			Date nowdate = new Date();
			cal.setTime(nowdate);

			int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
			if (w < 0)
				w = 0;

			return String.valueOf(w);
		}

		/**
		 * Description: 获得某一月的第一天
		 * @param month
		 * @return
		 */
		public static Timestamp getFristDay(String month){
		    Calendar c = Calendar.getInstance();
		    int year = c.get(Calendar.YEAR);
		    String str = year+"-"+month+"-1 00:00:00";
		    Timestamp d = null;
		    try {
		        d = stringToTime(str);
		    } catch (Exception e) {
		        e.printStackTrace();
		    }
		        return d;
		    }

		public static String getFristDayFortUtil(String month) {
			Calendar c = Calendar.getInstance();
		    int year = c.get(Calendar.YEAR);
		    if (month.length() < 2) {
		    	month = "0" + month;
		    }
		    String str = year+"-"+month+"-01";
		    return str;
		}

		public static String getMonthFristDay2() {
			return getFristDayFortUtil(getMonth());
		}

		/**
		 * Description: 获得当前月的第一天
		 * @return
		 */
		public static Timestamp getMonthFristDay() {
		    return getFristDay(getMonth());
		}
		/**
		 * Description: 获得本周的星期一的日期,以星期日为第一天
		 * @return
		 */
		public static String getMondayOFWeek(){
			Calendar cd = Calendar.getInstance();
			// 获得今天是一周的第几天，星期日是第一天，星期一是第二天......
			int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK)-1; //因为按中国礼拜一作为第一天所以这里减1
			int mondayPlus = 0;
			if (dayOfWeek == 1) {
			   mondayPlus = 0;
			} else {
			mondayPlus= 1-dayOfWeek ;
			}
			GregorianCalendar currentDate = new GregorianCalendar();
			currentDate.add(GregorianCalendar.DATE, mondayPlus);
			Date monday = currentDate.getTime();
			DateFormat df = DateFormat.getDateInstance();
			String preMonday = df.format(monday);
			return preMonday;
		}
		/**
		 * Description: 获得本周的星期一的日期，以星期一为第一天
		 * @return
		 */
		 public static String getMondayOfThisWeek() {
			  Calendar c = Calendar.getInstance();
			  int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
			  if (day_of_week == 0)
			   day_of_week = 7;
			  c.add(Calendar.DATE, -day_of_week + 1);
			  SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
			  return df2.format(c.getTime());
		 }

		 /**
		 * @description 根据固定日期和周几获得日期
		 * 
		 * @date 2016年7月28日
		 */
		public static String getDateByWeek(String date, int dayOfWeek) {
			int result = getWeekCount(date);
			return weekMontoSun(Integer.parseInt(getYear()), Integer.parseInt(getMonth()), result-1, dayOfWeek);
		}

		 /**
		 * @description 获得固定年月日的周数
		 * 
		 * @date 2016年7月28日
		 */
		public static int getWeekCount(String str){
		     SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
		     int week = 1;
		     try {
				Date date =sdf.parse(str);
				 Calendar calendar = Calendar.getInstance();
				 calendar.setTime(date);
				 calendar.setFirstDayOfWeek(Calendar.MONDAY); //设置每周的第一天为星期一
				 calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);//每周从周一开始
				 //第几周
				 week = calendar.get(Calendar.WEEK_OF_MONTH);
				 //第几天，从周日开始
				 //int day = calendar.get(Calendar.DAY_OF_WEEK);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		     return week;
		 }

		 /**
		 * @description 根据年，月，周数，第几周获得日期
		 * 
		 * @date 2016年7月28日
		 */
		public static String weekMontoSun(int year,int month,int weekOfMonth,int dayOfWeek){
			   Calendar c = Calendar.getInstance();
			   c.set(year, month-1, 1); // x年 y月 1号
			   int i_week_day = c.get(Calendar.DAY_OF_WEEK); //如果i_week_day =1 的话 实际上是周日
			   int weekDay = 0;
			   if(i_week_day == 1){ //dayOfWeek+1 就是星期几（星期日 为 1）
				  weekDay = (weekOfMonth-1)*7 + dayOfWeek+1;
			   }else{
		          weekDay = 7-i_week_day+1 + (weekOfMonth-1)*7 + dayOfWeek +1;
			   }
			   c.set(Calendar.DATE, weekDay); //在当月1号的基础上加上相应的天数
			   SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			   return sf.format(c.getTime());
		 }

	 	/**
	     * 当前季度的开始时间，即2012-01-1 00:00:00
	     *
	     * @return
	     */
	    public  static String getCurrentQuarterStartTime() {
	    	SimpleDateFormat d = new SimpleDateFormat();
			d.applyPattern("yyyy-MM-dd");
	        Calendar c = Calendar.getInstance();
	        int currentMonth = c.get(Calendar.MONTH) + 1;
	        String now = null;
	        try {
	            if (currentMonth >= 1 && currentMonth <= 3)
	                c.set(Calendar.MONTH, 0);
	            else if (currentMonth >= 4 && currentMonth <= 6)
	                c.set(Calendar.MONTH, 3);
	            else if (currentMonth >= 7 && currentMonth <= 9)
	                c.set(Calendar.MONTH, 4);
	            else if (currentMonth >= 10 && currentMonth <= 12)
	                c.set(Calendar.MONTH, 9);
	            c.set(Calendar.DATE, 1);
	             Date dt = c.getTime();
	            now = String.valueOf((d.format(dt)));
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return now;
	    }

		    /**
		     * 当前季度的结束时间，即2012-03-31 23:59:59
		     *
		     * @return
		     */
		    public  static String getCurrentQuarterEndTime() {
		    	SimpleDateFormat d = new SimpleDateFormat();
				d.applyPattern("yyyy-MM-dd");
		        Calendar c = Calendar.getInstance();
		        int currentMonth = c.get(Calendar.MONTH) + 1;
		        String now = null;
		        try {
		            if (currentMonth >= 1 && currentMonth <= 3) {
		                c.set(Calendar.MONTH, 2);
		                c.set(Calendar.DATE, 31);
		            } else if (currentMonth >= 4 && currentMonth <= 6) {
		                c.set(Calendar.MONTH, 5);
		                c.set(Calendar.DATE, 30);
		            } else if (currentMonth >= 7 && currentMonth <= 9) {
		                c.set(Calendar.MONTH, 8);
		                c.set(Calendar.DATE, 30);
		            } else if (currentMonth >= 10 && currentMonth <= 12) {
		                c.set(Calendar.MONTH, 11);
		                c.set(Calendar.DATE, 31);
		            }
		            Date dt = c.getTime();
		            now = String.valueOf((d.format(dt)));
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		        return now;
		    }


			/**
			 * 获取指定时间之前或之后几小时 hour
			 * @param hour
			 * @return date
			 */
		    public static Date getTimeByHour(Date date, int hour) {

		        Calendar calendar = Calendar.getInstance();
		        calendar.setTime(date);
		        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + hour);

		        return calendar.getTime();
		    }
		    /**
		     * @description 获取指定时间之前或之后几天 日期（Date）
		     * 
		     * @date 2018年5月3日
		     * @param date
		     * @param days
		     * @return
		     */
		    public static Date getDateByDay(Date date, int days) {
		        Calendar calendar = Calendar.getInstance();
		        calendar.setTime(date);
		        calendar.add(Calendar.DAY_OF_MONTH, days);
		        return calendar.getTime();
		    }
		    /**
		     * @description 获取指定时间之前或之后几天 日期（字符串DateTime）
		     * 
		     * @date 2018年5月3日
		     * @param date
		     * @param days
		     * @return
		     */
		    public static String getDateTimeByDay(Date date, int days) {
		    	return getDateTime(getDateByDay(date, days));
		    }

		    /**
			 * 获取指定时间之前或之后几分钟 日期（Date）
			 * @param minute
			 * @return date
			 */
		    public static Date getDateByMinute(Date date, int minute) {
		    	Calendar calendar = Calendar.getInstance();
		    	calendar.setTime(date);
		    	calendar.add(Calendar.MINUTE, minute);
		    	return calendar.getTime();
		    }
		    /**
		     * @description 获取指定时间之前或之后几分钟日期（字符串DateTime）
		     * 
		     * @date 2018年4月28日
		     * @param date
		     * @param second
		     * @return
		     */
		    public static String getDateTimeByMinute(Date date, int second) {
		    	return getDateTime(getDateByMinute(date, second));
		    }
		    /**
		     * @description 获取指定时间之前或之后几秒日期（Date）
		     * 
		     * @date 2018年4月28日
		     * @param date
		     * @param second
		     * @return
		     */
		    public static Date getDateBySecond(Date date, int second) {
		        Calendar calendar = Calendar.getInstance();
		        calendar.setTime(date);
		        calendar.add(Calendar.SECOND, second);
		        return calendar.getTime();
		    }
		    /**
		     * @description 获取指定时间之前或之后几秒日期（字符串DateTime）
		     * 
		     * @date 2018年4月28日
		     * @param date
		     * @param second
		     * @return
		     */
		    public static String getDateTimeBySecond(Date date, int second) {
		    	return getDateTime(getDateBySecond(date, second));
		    }


		 // 获得上周星期日的日期
			public static String getPreviousWeekSunday() {

				int weeks = 0;
				weeks--;
				int mondayPlus = getMondayPlus();
				GregorianCalendar currentDate = new GregorianCalendar();
				currentDate.add(GregorianCalendar.DATE, mondayPlus + weeks);
				Date monday = currentDate.getTime();
				DateFormat df = DateFormat.getDateInstance();
				String preMonday = df.format(monday);
				return preMonday;
			}

//			 获得上周星期一的日期
			public static String getPreviousWeekday() {
				int weeks = -1;
				int mondayPlus = getMondayPlus();
				GregorianCalendar currentDate = new GregorianCalendar();
				currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 * weeks);
				Date monday = currentDate.getTime();
				DateFormat df = DateFormat.getDateInstance();
				String preMonday = df.format(monday);
				return preMonday;
			}


//			 获得四周前星期一的日期
			public static String get4PreviousWeekday() {
				int weeks = -1;
				int mondayPlus = getMondayPlus();
				GregorianCalendar currentDate = new GregorianCalendar();
				currentDate.add(GregorianCalendar.DATE, mondayPlus + 28 * weeks);
				Date monday = currentDate.getTime();
				DateFormat df = DateFormat.getDateInstance();
				String preMonday = df.format(monday);
				return preMonday;
			}

			// 获得当前日期与本周日相差的天数
			private static int getMondayPlus() {
				Calendar cd = Calendar.getInstance();
				// 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
				int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一作为第一天所以这里减1
				if(dayOfWeek==0)dayOfWeek=7;
				if (dayOfWeek == 1) {
					return 0;
				} else {
					return 1 - dayOfWeek;
				}
			}
			/**
			 * 解析Timestamp
			 *
			 * @param timestampStr
			 *            时间戳字符串
			 * @param pattern
			 *            指定模式
			 * @return Timestamp
			 * @throws RuntimeException
			 */
			public static Timestamp parseTimestamp(String timestampStr, String pattern) {
				SimpleDateFormat simpleFormat = new SimpleDateFormat(pattern);
				Timestamp Timestamp = null;
					try {
						Timestamp = new Timestamp(simpleFormat.parse(timestampStr)
								.getTime());
					} catch (ParseException e) {
						e.printStackTrace();
					}
				return Timestamp;
			}

			/**
			 * 解析Timestamp
			 *
			 * @param year
			 *            年
			 * @param month
			 *            月
			 * @param day
			 *            日
			 * @param hour
			 *            小时
			 * @param minute
			 *            分钟
			 * @param second
			 *            秒
			 * @return Timestamp
			 * @throws RuntimeException
			 */
			public static Timestamp parseTimestamp(int year, int month, int day,
					int hour, int minute, int second) throws RuntimeException {
				validateTimestamp(year, month, day, hour, minute, second);
				Calendar calendar = Calendar.getInstance();
				calendar.set(year, month - 1, day, hour, minute, second);
				Timestamp timestamp = new Timestamp(calendar.getTimeInMillis());
				return timestamp;
			}

			private static void validateTimestamp(int year, int month, int day,
					int hour, int minute, int second) throws RuntimeException {
				if (year < 1900 || year > 2400) {
					throw new RuntimeException("Date parse error: the year is invalid.");
				}
				if (month < 1 || month > 12) {
					throw new RuntimeException("Date parse error: the month is invalid.");
				}
				if (day < 1 || day > 31) {
					throw new RuntimeException("Date parse error: the day is invalid.");
				}
				if (hour < 0 || hour > 23) {
					throw new RuntimeException("Date parse error: the day is invalid.");
				}
				if (minute < 0 || minute > 59) {
					throw new RuntimeException("Date parse error: the day is invalid.");
				}
				if (second < 0 || second > 59) {
					throw new RuntimeException("Date parse error: the day is invalid.");
				}
			}

			@SuppressWarnings("unused")
			private static void validateDate(int year, int month, int day)
					throws RuntimeException {
				if (year < 1900 || year > 2400)
					throw new RuntimeException("Date parse error: the year is invalid.");
				if (month < 1 || month > 12)
					throw new RuntimeException("Date parse error: the month is invalid.");
				if (day < 1 || day > 31)
					throw new RuntimeException("Date parse error: the day is invalid.");
			}

			/**
			 * 得到当前时间的Timestamp对象
			 *
			 * @return Timestamp
			 */
			public static Timestamp currentTimestamp() {
				return new Timestamp(System.currentTimeMillis());
			}
			/**
			 * 获取当前年的第一天
			 *
			 * @return String
			 */
			public static String firstDayForYear() {
				Calendar calendar = Calendar.getInstance();
				return String.valueOf(calendar.get(Calendar.YEAR)) + "-01-01";
			}


		    /**
		     * @description 测试main方法
		     * 
		     * @date 2016年7月28日
		     */
		    public static void main(String[] args) {
		    	System.out.println(DateUtils.getDate(new Date(1348831860), YMD_HMS_PATTERN));
//				System.out.println(DateUtils.getRelativeDate(10));
		    }

	/**
	 * 获取当前系统时间最近12月的年月（含当月）
	 * 2018-04~2019-03
	 */
	public static String getLatestNumMonth(int num){
		Date date = new Date();
		Calendar from  =  Calendar.getInstance();
		from.setTime(date);
		from.add(Calendar.MONTH, -num);//num个月前
		String str2 = from.get(Calendar.YEAR)+"-"+fillZero(from.get(Calendar.MONTH)+1);
		return str2;
	}
	/**
	 * 格式化月份
	 */
	public static String fillZero(int i){
		String month = "";
		if(i<10){
			month = "0" + i;
		}else{
			month = String.valueOf(i);
		}
		return month;
	}

}

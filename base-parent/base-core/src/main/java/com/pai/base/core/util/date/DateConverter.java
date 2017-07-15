package com.pai.base.core.util.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import com.pai.base.core.util.string.StringUtils;

public class DateConverter {
	private final static SimpleDateFormat dateTimeFormatter;
	private final static String dateTimePattern = "yyyy-MM-dd HH:mm:ss";

	private final static SimpleDateFormat dateLineFormatter;
	private final static String dateLinePattern = "yyyy-MM-dd";

	private final static SimpleDateFormat timeLineFormatter;
	private final static String timeLinePattern = "HH-mm";

	private final static String shortDatePattern = "yyMMdd";

	private static String timePattern = "HH:mm";

	static {
		dateTimeFormatter = new SimpleDateFormat(dateTimePattern);
		dateLineFormatter = new SimpleDateFormat(dateLinePattern);
		timeLineFormatter = new SimpleDateFormat(timeLinePattern);
	}

	public static final String now() {
		return dateTimeFormatter.format(new Date());
	}

	public static final String nowDateLine() {
		return dateLineFormatter.format(new Date());
	}

	public static final String nowTimeLine() {
		return timeLineFormatter.format(new Date());
	}

	public static final String toString(long time) {
		return dateTimeFormatter.format(new Date(time));
	}

	/**
	 * 日期转成字符串
	 * 
	 * @param aDate
	 * @return
	 */
	public static final String toString(Date aDate) {
		return dateTimeFormatter.format(aDate);
	}

	public static final String toString(Date aDate, String pattern) {
		if (pattern == null || aDate == null) {
			return "";
		}
		SimpleDateFormat df = null;
		String returnValue = "";
		df = new SimpleDateFormat(pattern);
		returnValue = df.format(aDate);

		return (returnValue);
	}

	public static final String toGMT(Date date) {
		try {
			SimpleDateFormat format = new SimpleDateFormat(
					"EEE, dd-MMM-yyyy HH:mm:ss zzz");
			Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
			format.setCalendar(cal);
			return format.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public static Date toDate(String strDate) throws ParseException {
		if (StringUtils.isEmpty(strDate)) {
			return null;
		}
		Date date = null;
		try {
			if (strDate.length() > 11) {
				date = dateTimeFormatter.parse(strDate);
			} else {
				date = dateLineFormatter.parse(strDate);
			}
		} catch (ParseException pe) {
			pe.printStackTrace();
			throw new ParseException(pe.getMessage(), pe.getErrorOffset());

		}
		return date;
	}

	public static final Date toDate(String strDate, String pattern)
			throws ParseException {
		if (StringUtils.isEmpty(strDate)) {
			return null;
		}
		SimpleDateFormat df = null;
		Date date = null;
		df = new SimpleDateFormat(pattern);

		try {
			date = df.parse(strDate);
		} catch (ParseException pe) {
			pe.printStackTrace();
			throw new ParseException(pe.getMessage(), pe.getErrorOffset());
		}

		return (date);
	}
}

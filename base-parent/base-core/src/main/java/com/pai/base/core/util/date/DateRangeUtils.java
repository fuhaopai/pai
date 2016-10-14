package com.pai.base.core.util.date;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.pai.base.core.constants.SimpleCalcType;
import com.pai.base.core.constants.DateField;
/**
 * 
 * <pre> 
 * 描述：TODO
 * 构建组：base-core
 * 作者：唯心
 * 邮箱:  craft6@qq.com
 * 日期:2014-4-6-下午1:31:24
 * 版权：唯心六艺工作室版权所有
 * </pre>
 */
public class DateRangeUtils {		
	public static DateData initNow(){
		return new DateData(new java.util.Date());
	}
	public static DateData init(Date date){
		return new DateData(date);
	}
	
	public static class DateData{
		private List<DateFieldData> dateFieldDatas = new ArrayList<DateFieldData>();
		
		private Date date;
		
		public DateData(Date date){
			this.date = date;
		}
		
		public Date getStartOfDay(){
			Calendar calendar = prepare();
			setStartOfDay(calendar);
			return calendar.getTime();
		}
		public Date getStartOfWeek(){
			Calendar calendar = prepare();			
			calendar.set(Calendar.DAY_OF_WEEK, 1);
			setStartOfDay(calendar);
			return calendar.getTime();
		}
		public Date getStartOfMonth(){
			Calendar calendar = prepare();			
			calendar.set(Calendar.DAY_OF_MONTH, 1);
			setStartOfDay(calendar);
			return calendar.getTime();
		}
		public Date getStartOfYear(){
			Calendar calendar = prepare();			
			calendar.set(Calendar.DAY_OF_YEAR, 1);
			setEndOfDay(calendar);
			return calendar.getTime();
		}
		public Date getEndOfDay(){
			Calendar calendar = prepare();
			setEndOfDay(calendar);
			return calendar.getTime();
		}		
		public Date getEndOfWeek(){
			Calendar calendar = prepare();			
			calendar.set(Calendar.DAY_OF_WEEK, 7);
			setEndOfDay(calendar);
			return calendar.getTime();
		}	
		public Date getEndOfYear(){
			Calendar calendar = prepare();			
			calendar.add(Calendar.YEAR, 1);
			calendar.set(Calendar.DAY_OF_YEAR, 1);
			calendar.add(Calendar.DATE, -1);		
			setEndOfDay(calendar);
			return calendar.getTime();
		}			
		
		private Calendar prepare(){
			Calendar calendar = Calendar.getInstance();
			if(dateFieldDatas.size()>0){
				date = calc();
			}
			calendar.setTime(date);
			return calendar;
		}
		private void setStartOfDay(Calendar calendar){
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
		}
		private void setEndOfDay(Calendar calendar){
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			calendar.set(Calendar.MILLISECOND, 999);			
		}
		
		/**
		 * 增减年
		 * @param year 整数，如1、2、3、-1、-2等
		 * @return 修改后日期
		 */
		public Date changeYear(int year){
			DateFieldData dateFieldData = new DateFieldData(DateField.YEAR, year, SimpleCalcType.PLUS);
			dateFieldDatas.add(dateFieldData);
			return calc();
		}
		
		/**
		 * 增减月
		 * @param month 整数，如1、2、3、-1、-2等
		 * @return 修改后日期
		 */
		public Date changeMonth(int month){
			DateFieldData dateFieldData = new DateFieldData(DateField.MONTH, month, SimpleCalcType.PLUS);
			dateFieldDatas.add(dateFieldData);
			return calc();
		}
		
		/**
		 * 增减日
		 * @param day 整数，如1、2、3、-1、-2等
		 * @return 修改后日期
		 */
		public Date changeDay(int day){
			DateFieldData dateFieldData = new DateFieldData(DateField.DAY, day, SimpleCalcType.PLUS);
			dateFieldDatas.add(dateFieldData);
			return calc();
		}		
		
		/**
		 * 增减时
		 * @param hour 整数，如1、2、3、-1、-2等
		 * @return 修改后日期
		 */
		public Date changeHour(int hour){
			DateFieldData dateFieldData = new DateFieldData(DateField.HOUR, hour, SimpleCalcType.PLUS);
			dateFieldDatas.add(dateFieldData);
			return calc();
		}
		
		/**
		 * 增减分
		 * @param minute 整数，如1、2、3、-1、-2等
		 * @return 修改后日期
		 */
		public Date changeMinute(int minute){
			DateFieldData dateFieldData = new DateFieldData(DateField.MINUTES, minute, SimpleCalcType.PLUS);
			dateFieldDatas.add(dateFieldData);
			return calc();
		}		
		
		/**
		 * 增减秒
		 * @param second 整数，如1、2、3、-1、-2等
		 * @return 修改后日期
		 */
		public Date changeSecond(int second){
			DateFieldData dateFieldData = new DateFieldData(DateField.SECOND, second, SimpleCalcType.PLUS);
			dateFieldDatas.add(dateFieldData);
			return calc();
		}		
		
		public Date change(DateField dateField_,int number_,SimpleCalcType calcDirect_){
			DateFieldData dateFieldData = new DateFieldData(dateField_, number_, calcDirect_);
			dateFieldDatas.add(dateFieldData);
			return calc();
		}
		
		public Date change(int year,int month,int day,int hour,int minute,int second){
			if(year!=0){
				if(year>0){
					DateFieldData dateFieldData = new DateFieldData(DateField.YEAR, year, SimpleCalcType.PLUS);
					dateFieldDatas.add(dateFieldData);
				}else{
					DateFieldData dateFieldData = new DateFieldData(DateField.YEAR, year, SimpleCalcType.MINUS);
					dateFieldDatas.add(dateFieldData);
				}
			}
			if(month!=0){
				if(month>0){
					DateFieldData dateFieldData = new DateFieldData(DateField.MONTH, year, SimpleCalcType.PLUS);
					dateFieldDatas.add(dateFieldData);
				}else{
					DateFieldData dateFieldData = new DateFieldData(DateField.MONTH, year, SimpleCalcType.MINUS);
					dateFieldDatas.add(dateFieldData);
				}
			}
			if(day!=0){
				if(day>0){
					DateFieldData dateFieldData = new DateFieldData(DateField.DAY, year, SimpleCalcType.PLUS);
					dateFieldDatas.add(dateFieldData);
				}else{
					DateFieldData dateFieldData = new DateFieldData(DateField.DAY, year, SimpleCalcType.MINUS);
					dateFieldDatas.add(dateFieldData);
				}
			}
			if(hour!=0){
				if(hour>0){
					DateFieldData dateFieldData = new DateFieldData(DateField.HOUR, year, SimpleCalcType.PLUS);
					dateFieldDatas.add(dateFieldData);
				}else{
					DateFieldData dateFieldData = new DateFieldData(DateField.HOUR, year, SimpleCalcType.MINUS);
					dateFieldDatas.add(dateFieldData);
				}
			}
			if(minute!=0){
				if(minute>0){
					DateFieldData dateFieldData = new DateFieldData(DateField.MINUTES, year, SimpleCalcType.PLUS);
					dateFieldDatas.add(dateFieldData);
				}else{
					DateFieldData dateFieldData = new DateFieldData(DateField.MINUTES, year, SimpleCalcType.MINUS);
					dateFieldDatas.add(dateFieldData);
				}
			}
			if(second!=0){
				if(second>0){
					DateFieldData dateFieldData = new DateFieldData(DateField.SECOND, year, SimpleCalcType.PLUS);
					dateFieldDatas.add(dateFieldData);
				}else{
					DateFieldData dateFieldData = new DateFieldData(DateField.SECOND, year, SimpleCalcType.MINUS);
					dateFieldDatas.add(dateFieldData);
				}
			}
			return calc();
		}
		
		private Date calc(){
			DateCalc dateCalc = new DateCalc(this);
			return dateCalc.calc();
		}
		List<DateFieldData> getDateFieldDatas(){
			return dateFieldDatas;
		}
		Date getDate(){
			return date;
		}
	}
	public static class DateFieldData{
		private DateField dateField;
		private int number;
		private SimpleCalcType calcDirect;
		public DateFieldData(DateField dateField_,int number_,SimpleCalcType calcDirect_){
			dateField = dateField_;
			number = number_;
			calcDirect = calcDirect_;
		}
		protected DateField getDateField() {
			return dateField;
		}
		protected int getNumber() {
			return number;
		}
		protected SimpleCalcType getCalcDirect() {
			return calcDirect;
		}		
	}
	public static class DateCalc{
		private DateData dateData;
		private Calendar calendar;
		public DateCalc(DateData dateData_){
			dateData = dateData_;
			calendar = Calendar.getInstance();
			calendar.setTime(dateData.getDate());
		}

		public Date calc(){
			for(DateFieldData dateFieldData:dateData.getDateFieldDatas()){
				if(dateFieldData.getDateField().equals(DateField.YEAR)){
					if(dateFieldData.getCalcDirect().equals(SimpleCalcType.PLUS)){
						calendar.add(Calendar.YEAR, dateFieldData.getNumber());
					}else if(dateFieldData.getCalcDirect().equals(SimpleCalcType.PLUS)){
						calendar.add(Calendar.YEAR, dateFieldData.getNumber() * -1);
					}
				}
				if(dateFieldData.getDateField().equals(DateField.MONTH)){
					if(dateFieldData.getCalcDirect().equals(SimpleCalcType.PLUS)){
						calendar.add(Calendar.MONTH, dateFieldData.getNumber());
					}else if(dateFieldData.getCalcDirect().equals(SimpleCalcType.PLUS)){
						calendar.add(Calendar.MONTH, dateFieldData.getNumber() * -1);
					}
				}				
				if(dateFieldData.getDateField().equals(DateField.DAY)){
					if(dateFieldData.getCalcDirect().equals(SimpleCalcType.PLUS)){
						calendar.add(Calendar.DATE, dateFieldData.getNumber());
					}else if(dateFieldData.getCalcDirect().equals(SimpleCalcType.PLUS)){
						calendar.add(Calendar.DATE, dateFieldData.getNumber() * -1);
					}
				}	
				if(dateFieldData.getDateField().equals(DateField.HOUR)){
					if(dateFieldData.getCalcDirect().equals(SimpleCalcType.PLUS)){
						calendar.add(Calendar.HOUR, dateFieldData.getNumber());
					}else if(dateFieldData.getCalcDirect().equals(SimpleCalcType.PLUS)){
						calendar.add(Calendar.HOUR, dateFieldData.getNumber() * -1);
					}
				}	
				if(dateFieldData.getDateField().equals(DateField.MINUTES)){
					if(dateFieldData.getCalcDirect().equals(SimpleCalcType.PLUS)){
						calendar.add(Calendar.MINUTE, dateFieldData.getNumber());
					}else if(dateFieldData.getCalcDirect().equals(SimpleCalcType.PLUS)){
						calendar.add(Calendar.MINUTE, dateFieldData.getNumber() * -1);
					}
				}	
				if(dateFieldData.getDateField().equals(DateField.SECOND)){
					if(dateFieldData.getCalcDirect().equals(SimpleCalcType.PLUS)){
						calendar.add(Calendar.SECOND, dateFieldData.getNumber());
					}else if(dateFieldData.getCalcDirect().equals(SimpleCalcType.PLUS)){
						calendar.add(Calendar.SECOND, dateFieldData.getNumber() * -1);
					}
				}					
			}
			return calendar.getTime();
		}
	}
}

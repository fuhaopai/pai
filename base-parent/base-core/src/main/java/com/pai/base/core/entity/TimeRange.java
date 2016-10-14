package com.pai.base.core.entity;

import java.util.Date;

import com.pai.base.core.constants.RangeTimeType;
import com.pai.base.core.util.date.DateConverter;
import com.pai.base.core.util.date.DateRangeUtils;
import com.pai.base.core.util.string.StringCollections;

public class TimeRange {
	private Date begin;
	private Date end;
	public TimeRange(String timeRangeString,String token,RangeTimeType endDateTimeType){
		String[] ranges = StringCollections.toArray(timeRangeString, token);
		try {
			begin = DateConverter.toDate(ranges[0]);
			begin = DateRangeUtils.init(begin).getStartOfDay();
			end = DateConverter.toDate(ranges[1]);
			if(endDateTimeType.equals(RangeTimeType.StartOfDay)){
				end = DateRangeUtils.init(end).getStartOfDay();
			}else{
				end = DateRangeUtils.init(end).getEndOfDay();
			}				
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	public TimeRange(Date begin,Date end){
		this.begin = begin;
		this.end = end;
	}
	public Date getBegin() {
		return begin;
	}
	public Date getEnd() {
		return end;
	}
}

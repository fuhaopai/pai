package com.pai.base.core.util;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.dozer.DozerConverter;

import com.pai.base.core.util.date.DateConverter;

/**
 * 描述:转换String和Date.
 *
 */
public class MapperDateConverter extends DozerConverter<String, Date> {

	public MapperDateConverter() {
		super(String.class, Date.class);
	}

	@Override
	public Date convertTo(String source, Date destination) {
		try {
			return StringUtils.isBlank(source) ? null : DateConverter.toDate(source);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String convertFrom(Date source, String destination) {
		return source != null ? DateConverter.toString(source) : null;
	}

}

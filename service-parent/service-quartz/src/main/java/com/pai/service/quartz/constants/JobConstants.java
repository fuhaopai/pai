package com.pai.service.quartz.constants;

public class JobConstants {
	public final static class PLAN_TYPE{
		public final static String ONE_TIME="one_time";
		public final static String EXPR="expr";
		public final static String EXPR_AT_ONCE="expr_at_once";
	}
	public final static class DATA_TYPE{
		public final static String INT="int";
		public final static String LONG="long";
		public final static String DOUBLE="double";
		public final static String DATE="date";
		public final static String STRING="string";
	}
	public final static class LOG_STATUS{
		public final static String SUCCESS="success";
		public final static String FAILURE="failure";
	}
	
	public final static class EXECUTE{
		public final static String METHOD_ID="methodId";
		public final static String TASK_NAME="taskName";
		public final static String METHOD_NAME="methodName";
		public final static String CLASS_NAME="className";
		public final static String PARAMS="params";
		public final static String PARAM_TYPES="paramTypes";
	}
}

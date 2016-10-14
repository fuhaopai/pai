package com.pai.base.core.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.beanutils.converters.SqlDateConverter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

import com.pai.base.core.util.date.BeanUtilDateConverter;
import com.pai.base.core.util.string.StringUtils;

/**
 * 扩展Apache Commons BeanUtils, 提供一些反射方面缺失功能的封装.
 */
public class BeanUtils extends org.apache.commons.beanutils.BeanUtils {
	
	protected static final Log	logger	= LogFactory.getLog(BeanUtils.class);

	static {
		//注册sql.date的转换器，即允许BeanUtils.copyProperties时的源目标的sql类型的值允许为空
		ConvertUtils.register(new BeanUtilDateConverter(), java.util.Date.class);		
	}
	
	/**
	 * 暴力获取对象变量值,忽略private,protected修饰符的限制.
	 * 
	 * @throws NoSuchFieldException
	 *             如果没有该Field时抛出.
	 */
	public static Object forceGetProperty(Object object, String propertyName)
			throws NoSuchFieldException {
		Assert.notNull(object);
		Assert.hasText(propertyName);

		Field field = getDeclaredField(object, propertyName);

		boolean accessible = field.isAccessible();
		field.setAccessible(true);

		Object result = null;
		try {
			result = field.get(object);
		} catch (IllegalAccessException e) {
			logger.info("error wont' happen");
		}
		field.setAccessible(accessible);
		return result;
	}

	/**
	 * 暴力设置对象变量值,忽略private,protected修饰符的限制.
	 * 
	 * @throws NoSuchFieldException
	 *             如果没有该Field时抛出.
	 */
	public static void forceSetProperty(Object object, String propertyName,
			Object newValue) throws NoSuchFieldException {
		Assert.notNull(object);
		Assert.hasText(propertyName);

		Field field = getDeclaredField(object, propertyName);
		boolean accessible = field.isAccessible();
		field.setAccessible(true);
		try {
			field.set(object, newValue);
		} catch (IllegalAccessException e) {
			logger.info("Error won't happen");
		}
		field.setAccessible(accessible);
	}

	/**
	 * 循环向上转型,获取对象的DeclaredField.
	 * 
	 * @throws NoSuchFieldException
	 *             如果没有该Field时抛出.
	 */
	public static Field getDeclaredField(Class clazz, String propertyName)
			throws NoSuchFieldException {
		Assert.notNull(clazz);
		Assert.hasText(propertyName);
		for (Class superClass = clazz; superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			try {
				return superClass.getDeclaredField(propertyName);
			} catch (NoSuchFieldException e) {
				// Field不在当前类定义,继续向上转型
			}
		}
		throw new NoSuchFieldException("No such field: " + clazz.getName()
				+ '.' + propertyName);
	}

	/**
	 * 循环向上转型,获取对象的DeclaredField.
	 * 
	 * @throws NoSuchFieldException
	 *             如果没有该Field时抛出.
	 */
	public static Field getDeclaredField(Object object, String propertyName)
			throws NoSuchFieldException {
		Assert.notNull(object);
		Assert.hasText(propertyName);
		return getDeclaredField(object.getClass(), propertyName);
	}

	/**
	 * 按Filed的类型取得Field列表.
	 */
	public static List<Field> getFieldsByType(Object object, Class type) {
		List<Field> list = new ArrayList<Field>();
		Field[] fields = object.getClass().getDeclaredFields();
		for (Field field : fields) {
			if (field.getType().isAssignableFrom(type)) {
				list.add(field);
			}
		}
		return list;
	}

	/**
	 * 获得field的getter函数,如果找不到该方法,返回null.
	 */
	public static Method getGetterMethod(Class type, String fieldName) {
		try {
			return type.getMethod(getGetterName(type, fieldName));
		} catch (NoSuchMethodException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 获得field的getter函数名称.
	 */
	public static String getGetterName(Class type, String fieldName) {
		Assert.notNull(type, "Type required");
		Assert.hasText(fieldName, "FieldName required");

		if (type.getName().equals("boolean")) {
			return "is" + StringUtils.upperFirst(fieldName);
		} else {
			return "get" + StringUtils.upperFirst(fieldName);
		}
	}

	/**
	 * 按FiledName获得Field的类型.
	 */
	public static Class getPropertyType(Class type, String name)
			throws NoSuchFieldException {
		return getDeclaredField(type, name).getType();
	}

	/**
	 * 暴力调用对象函数,忽略private,protected修饰符的限制.
	 * 
	 * @throws NoSuchMethodException
	 *             如果没有该Method时抛出.
	 */
	public static Object forceInvokeMethod(Object object, String methodName,
			Object... params) throws NoSuchMethodException {
		Assert.notNull(object);
		Assert.hasText(methodName);
		Class[] types = null;
		if(params!=null && params.length>0){
			types = new Class[params.length];
			for (int i = 0; i < params.length; i++) {
				types[i] = params[i].getClass();
			}
		}
		Class clazz = object.getClass();
		Method method = null;
		for (Class superClass = clazz; superClass != Object.class; superClass = superClass
				.getSuperclass()) {
			try {
				if(types!=null){
					method = superClass.getDeclaredMethod(methodName, types);
				}else{
					method = superClass.getMethod(methodName);
				}
				break;
			} catch (NoSuchMethodException e) {
				// 方法不在当前类定义,继续向上转型
			}
		}

		if (method == null)
			throw new NoSuchMethodException("No Such Method:"
					+ clazz.getSimpleName() + methodName);

		boolean accessible = method.isAccessible();
		method.setAccessible(true);
		Object result = null;
		try {
			result = method.invoke(object, params);
		} catch (Exception e) {
			ReflectionUtils.handleReflectionException(e);
		}
		method.setAccessible(accessible);
		return result;
	}
	
	public static List<Map<String,Object>> convertToMapList(List entityList){
		List<Map<String,Object>> mapList = new ArrayList<Map<String,Object>>();
		for(Object entity:entityList){
			try{
				Map<String,Object> entityMap = new HashMap<String,Object>();
				Field[] field = entity.getClass().getDeclaredFields();
				for(int i=0 ; i<field.length ; i++){     //遍历所有属性
					String propertyName = field[i].getName();    //获取属性的名字
					if (!field[i].getType().isAssignableFrom(Class.forName("java.util.Set"))) {
						Object value = getPropertyValue(entity,propertyName);
						entityMap.put(propertyName, value);
					}
					
				}
				field = entity.getClass().getSuperclass().getDeclaredFields();
				for(int j=0 ; j<field.length ; j++){     //遍历父类（Tbl）所有属性
					String propertyName = field[j].getName();    //获取属性的名字
					if (!field[j].getType().isAssignableFrom(Class.forName("java.util.Set"))) {
						Object value = getPropertyValue(entity,propertyName);
						entityMap.put(propertyName, value);
					}
				}
				mapList.add(entityMap);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return mapList;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static boolean isInherit(Class cls,Class parentClass)
	{
		return parentClass.isAssignableFrom(cls);
	}	
	
	private static Object getPropertyValue(Object entity,String propertyName){
		try{
			String methodName = "get"+StringUtils.upperFirst(propertyName);
			return forceInvokeMethod(entity,methodName);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}		
	} 
		
    public static void copyProperties(Object dest, Object orig)
            throws IllegalAccessException, InvocationTargetException {
    	ConvertUtils.register(new BeanUtilDateConverter(), java.util.Date.class); 
    	ConvertUtils.register(new SqlDateConverter(), java.util.Date.class);
    	ConvertUtils.register(new DateConverter(null), java.util.Date.class);    	
        BeanUtilsBean.getInstance().copyProperties(dest, orig);
    }
	
	private BeanUtils() {
	}
}

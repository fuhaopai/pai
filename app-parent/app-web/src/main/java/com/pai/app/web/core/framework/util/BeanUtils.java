package com.pai.app.web.core.framework.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 *    Bean对象帮助类
 *    @author Suoron
 *    2015-09-02
 * 
 */

public class BeanUtils{	
	public static ConcurrentHashMap<String, Object> default_values = new ConcurrentHashMap<String, Object>() ;
			
	private static Object getDefaultValue(String type){
		return default_values.get(type);
	}
	
	/**
	 *    为对象的所有为空属性赋初始值、如果有值则不发生改变
	 *    @param bean 需要过滤空值的VO
	 *    @param filter_default 是否默认值也过滤
	 */
	public static ConcurrentHashMap<String,Object> getNotNullProperties(Object bean,boolean filter_default) throws IllegalAccessException, InvocationTargetException,
	           IntrospectionException{
		
		ConcurrentHashMap<String,Object> propertiesMap = new ConcurrentHashMap<String,Object>();
		
        /*  获取bean信息 */  
        BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
        
        /* 获取bean的所有属性列表 */  
        PropertyDescriptor[] proDescrtptors = beanInfo.getPropertyDescriptors();
        
        if (proDescrtptors != null && proDescrtptors.length > 0) {
            for (PropertyDescriptor propDesc : proDescrtptors) {
            	Class clazz = propDesc.getPropertyType();            	
            	            	
            	Method getMethod = propDesc.getReadMethod();  
                Method setMethod = propDesc.getWriteMethod();
                
                if(getMethod == null || setMethod == null){
              	  continue;
              }
                
                if(getMethod.invoke(bean) == null  || (filter_default 
                		&& getMethod.invoke(bean).equals(getDefaultValue(clazz.getName())))){
                	  continue;
                }                
                propertiesMap.put(propDesc.getName(),getMethod.invoke(bean));
            }  
        }         
		return propertiesMap;
	}	
	/**
	 *    为对象的所有为空属性赋初始值、如果有值则不发生改变
	 *    @param bean 需要初始化的VO
	 */
	public static Object getNotNullVo(Object bean) throws IllegalAccessException, InvocationTargetException,
	           IntrospectionException{
		
        /*  获取bean信息 */  
        BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
        
        /* 获取bean的所有属性列表 */  
        PropertyDescriptor[] proDescrtptors = beanInfo.getPropertyDescriptors();
        
        if (proDescrtptors != null && proDescrtptors.length > 0) {
            for (PropertyDescriptor propDesc : proDescrtptors) {
            	Class clazz = propDesc.getPropertyType();            	
            	            	
            	Method getMethod = propDesc.getReadMethod();  
                Method setMethod = propDesc.getWriteMethod();
                
                if(getMethod == null || setMethod == null){
                	  continue;
                }
                
                if(getMethod.invoke(bean) == null){
                	setMethod.invoke(bean, getDefaultValue(clazz.getName()));                	
                }
            }  
        }         
		return bean;
	}
	
	/**
	 * <ul>
	 * <li>java.lang.BigDecimal</li>
	 * <li>java.lang.BigInteger</li>
	 * <li>boolean and java.lang.Boolean</li>
	 * <li>byte and java.lang.Byte</li>
	 * <li>char and java.lang.Character</li>
	 *  * <li>double and java.lang.Double</li>
	 * <li>float and java.lang.Float</li>
	 * <li>int and java.lang.Integer</li>
	 * <li>long and java.lang.Long</li>
	 * <li>short and java.lang.Short</li>
	 * <li>java.lang.String</li>
	 * <li>java.sql.Date</li>
	 * <li>java.sql.Time</li>
	 * <li>java.sql.Timestamp</li>
	 * </ul>
	 */	
	
	static{
		default_values.put("boolean", false);
		default_values.put("java.lang.Boolean", false);
		default_values.put("double", 0D);
		default_values.put("java.lang.Double", 0D);		
		default_values.put("float", 0.0f);
		default_values.put("java.lang.Float", 0.0f);		
		default_values.put("int", 0);
		default_values.put("java.lang.Integer", 0);
		default_values.put("long", 0L);
		default_values.put("java.lang.Long", 0L);			
		default_values.put("short", (short)0);
		default_values.put("java.lang.Short", (short)0);
		default_values.put("java.lang.String", "");		
	}	
	
   private static void copyProperty(Object dest, String name, Object value) throws InvocationTargetException
        ,IllegalAccessException,IntrospectionException{
       /*  获取bean信息 */   
       BeanInfo beanInfo = Introspector.getBeanInfo(dest.getClass());
       
       /*  获取bean的所有属性列表 */  
       PropertyDescriptor[] proDescrtptors = beanInfo.getPropertyDescriptors();
       
       /* 遍历属性列表，查找指定的属性 */  
       if (proDescrtptors != null && proDescrtptors.length > 0) {  
           for (PropertyDescriptor propDesc : proDescrtptors) {
        	   
           	  Method getMethod = propDesc.getReadMethod();  
              Method setMethod = propDesc.getWriteMethod();
              
               if(getMethod == null || setMethod == null){
               	  continue;
               }
               // 找到则写入属性值  
               if (propDesc.getName().equals(name)) {                   
                   // 写入属性值  
                   Object values[] = new Object[1];
                   values[0] = value;                   
                   setMethod.invoke(dest, values);  
                   break;  
               }  
           }  
       } 	      
   }	
   public static void copyProperties(Object dest, Object orig)
	        throws IllegalAccessException, InvocationTargetException,IntrospectionException {

	        // Validate existence of the specified beans
	        if (dest == null) {
	            throw new IllegalArgumentException
	                    ("No destination bean specified");
	        }
	        if (orig == null) {
	            throw new IllegalArgumentException("No origin bean specified");
	        }
	        
	        /*  获取bean信息 */  
	        BeanInfo origBeanInfo = Introspector.getBeanInfo(orig.getClass());
	        //BeanInfo destBeanInfo = Introspector.getBeanInfo(dest.getClass());
	        
	        /* 获取bean的所有属性列表 */  
	        PropertyDescriptor[] origDescriptors = origBeanInfo.getPropertyDescriptors();
	        //PropertyDescriptor[] destDescriptors = origBeanInfo.getPropertyDescriptors();
	        
	        for(PropertyDescriptor propDesc : origDescriptors){	        		        	                
	        	String name = propDesc.getName();
	        	
	           	 Method getMethod = propDesc.getReadMethod();  
	             Method setMethod =  propDesc.getWriteMethod();
	              
	              if(getMethod == null || setMethod == null){
	               	  continue;
	              }                
	             
	              Object value = propDesc.getReadMethod().invoke(orig);
                
                if(value == null){   /* 赋初始值 */
                	value = getDefaultValue(propDesc.getClass().getName());                   
                }                
                
                copyProperty(dest, name, value);                
	        }
	}  
   public static void copyProperties(Object dest, Map orig)
	        throws IllegalAccessException, InvocationTargetException,IntrospectionException {

	        // Validate existence of the specified beans
	        if (dest == null) {
	            throw new IllegalArgumentException
	                    ("No destination bean specified");
	        }
	        if (orig == null) {
	            throw new IllegalArgumentException("No origin bean specified");
	        }
	        
	        /*  获取bean信息 */  
	        BeanInfo destBeanInfo = Introspector.getBeanInfo(dest.getClass());
	        
	        /* 获取bean的所有属性列表 */  
	        PropertyDescriptor[] destDescriptors = destBeanInfo.getPropertyDescriptors();
	        
	        for(PropertyDescriptor propDesc : destDescriptors){
	        	
	           	 Method getMethod = propDesc.getReadMethod();  
	             Method setMethod =  propDesc.getWriteMethod();
	              
	             if(getMethod == null || setMethod == null){
	               	  continue;
	             }
	             
	        	String name = propDesc.getName();
                Object value = orig.get(name); 
                
                if(value == null){   /* 赋初始值 */
               	   value = getDefaultValue(propDesc.getClass().getName());                   
               }               
               copyProperty(dest, name, value);                
	        }
	}    
	public static void main(String args[]){	
/*		UIDEntity u1 = new UIDEntity("aaaa",null,null,false);		
		try{
		     System.out.println(BeanUtils.getNotNullProperties(u1, false).toString());
		}catch(Exception ex){
			ex.printStackTrace();
		}	*/	     
		/*
		testVo t1 = new testVo();
		testVo t2 = new testVo();
				
		try{
		     t1 = (testVo)BeanUtils.getNotNullVo(t1);
  		     t1.setAaaString("test property clone!!!");
		     BeanUtils.copyProperties(t2, t1);		 
		     
		     System.out.println(".getAaaString():"+t2.getAaaString());
		     System.out.println(".getAaa():"+t2.getAaa()); 
		}catch(Exception ex){
			ex.printStackTrace();
		}
		*/
		
		
		
	}			
}


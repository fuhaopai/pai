package com.pai.base.core.doc.util;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.pai.base.api.annotion.doc.AutoDocField;
import com.pai.base.api.annotion.doc.AutoDocMethod;
import com.pai.base.api.annotion.doc.AutoDocParam;
import com.pai.base.core.doc.bean.ConfigBean;
import com.pai.base.core.doc.bean.DocBean;
import com.pai.base.core.doc.bean.ParamBean;
import com.pai.base.core.doc.bean.ParamField;

public class DocHelper {

	private final static String SOURCEPATH = new File("").getAbsolutePath();

	private final static String SERVER_PATH = "\\src\\main\\java\\com\\pai";

	private List<Class<?>> clazzList;

	private List<DocBean> docBeans;

	public DocHelper() {
		clazzList = new ArrayList<>();
		docBeans = new ArrayList<>();
	}

	public void createDoc(ConfigBean config) throws Exception {
		File dir = new File(SOURCEPATH + File.separator + SERVER_PATH);
		loadServerClass(dir, config.isRpcServer());
		processServerClass(config.isRpcServer(), config.getPrefix());
		ITextPDFUtil.createPDF(docBeans, config);
	}

	private void loadServerClass(File dir, boolean isRpcServer) throws Exception {
		File[] fs = dir.listFiles();
		for (int i = 0; i < fs.length; i++) {
			if (fs[i].isDirectory()) {
				loadServerClass(fs[i], isRpcServer);
			} else {
				String absolutePath = fs[i].getAbsolutePath();
				String path = absolutePath.substring(absolutePath.indexOf("com"), absolutePath.length());
				if (path.contains(".java"))
					path = path.substring(0, path.indexOf(".java"));
				path = path.replace("\\", ".");
				if (isRpcServer && (path.indexOf("Service") > 0)) {
					Class<?> clazz = Class.forName(path);
					clazzList.add(clazz);
				}
			}
		}
	}

	private void processServerClass(boolean isRpcServer, String prefix) {
		for (Class<?> serverClass : clazzList) {
			System.out.println(">>>process serverClass " + serverClass.getSimpleName());
			String classMappingName = "";
			if (!isRpcServer) {
				RequestMapping classMapping = serverClass.getAnnotation(RequestMapping.class);
				if (classMapping != null && (classMapping.value().length > 0 || classMapping.path().length > 0)) {
					classMappingName = classMapping.value().length > 0 ? classMapping.value()[0]
							: classMapping.path()[0];
				}
			} else {
				classMappingName = serverClass.getSimpleName();
			}

			Method[] methods = serverClass.getMethods();
			if (methods != null && methods.length > 0) {
				processServerMethod(methods, classMappingName, isRpcServer, prefix);
			}
		}
	}

	private void processServerMethod(Method[] methods, String classMappingPath, boolean isRpcServer, String prefix) {
		for (Method method : methods) {
			AutoDocMethod docMethod = method.getAnnotation(AutoDocMethod.class);
			if (docMethod != null) {
				System.out.println(">>>>>>>process method " + method.getName());

				DocBean bean = new DocBean();
				bean.setAuthor(docMethod.author().getName());
				bean.setSort(docMethod.sort());

				bean.setActionName(docMethod.name());
				bean.setActionDesc(docMethod.description());
				bean.setCreateTime(docMethod.createTime());
				bean.setDeprecated(docMethod.deprecated());
				bean.setModelName(docMethod.module().getName());
				bean.setModifyRecodes(docMethod.modify());
				bean.setVersion(docMethod.cver().getDes());

				if (!isRpcServer) {
					RequestMapping methodMapping = method.getAnnotation(RequestMapping.class);
					if (methodMapping != null && methodMapping.value().length > 0 || methodMapping.path().length > 0) {
						String methodMappingPath = methodMapping.value().length > 0 ? methodMapping.value()[0]
								: methodMapping.path()[0];

						RequestMethod[] requestMethods = methodMapping.method();

						StringBuilder actionUrl = new StringBuilder();
						if (StringUtils.isNotBlank(prefix)) {
							actionUrl.append(prefix);
						}
						actionUrl.append(classMappingPath);
						actionUrl.append(methodMappingPath);
						bean.setActionUrl(actionUrl.toString());
						bean.setReqMethod(Arrays.toString(requestMethods));
					}
				} else {
					bean.setActionUrl(classMappingPath + '.' + method.getName());
					bean.setReqMethod("[RPC]");
				}

				buildReqParameters(method, bean, isRpcServer);
				buildRespParameters(method, bean);

				docBeans.add(bean);
			}
		}
	}

	private void buildReqParameters(Method method, DocBean bean, boolean isRpcServer) {

		ParamBean requestParam = null;
		// 请求参数
		Class<?>[] paramTypes = method.getParameterTypes();
		List<ParamField> paramFields = new ArrayList<>();
		if (paramTypes != null && paramTypes.length > 0) {
			for (Class<?> paramType : paramTypes) {
				//说明是一个类对象带来的参数
				if (paramType.getName().startsWith("com.")) {
					Field[] fields = paramType.getDeclaredFields();
					if (isRpcServer) {
						if (requestParam == null) {
							requestParam = new ParamBean();
							requestParam.setBeanName("");
						}
						if (!isRpcServer) {
							bean.setReqType("application/json");
						}
						loadParamField(paramType, requestParam, null);
					} else {
						loadReqParamField(bean, paramFields, paramType, fields);
					}

				} else {
					//说明是方法内部的非对象参数
					if (!paramType.getSimpleName().contains("Servlet")) {
						ParamField paramField = new ParamField();
						paramField.setType(paramType.getSimpleName());
						paramFields.add(paramField);
					}

				}

			}
		}
		
		Annotation[][] parameterAnnotations = method.getParameterAnnotations();  
		int i = 0;
		for (Annotation[] parameterAnnotation : parameterAnnotations) {  
            for (Annotation annotation : parameterAnnotation) {  
                if (annotation instanceof AutoDocParam) {  
                	AutoDocParam param = (AutoDocParam) annotation;  
                	ParamField paramField = paramFields.get(i++);
                	paramField.setName(param.name());
                	paramField.setNote(param.value());
                }  
            }  
        }  
		
		if (!isRpcServer && method.getAnnotation(RequestMapping.class).params().length > 0) {
			ParamField paramField = new ParamField();
			paramField.setName(Arrays.toString(method.getAnnotation(RequestMapping.class).params()));
			paramField.setType("String");
			paramField.setNote("");
			paramFields.add(paramField);
		}

		if (!isRpcServer && StringUtils.isBlank(bean.getReqType())) {
			bean.setReqType("application/x-www-form-urlencoded");
		}
		bean.setRequest(requestParam);
		bean.setParams(paramFields);
	}

	private void loadReqParamField(DocBean bean, List<ParamField> paramFields, Class<?> paramType, Field[] fields) {
		Class<?> supClass = paramType.getSuperclass();
		if (supClass != null && supClass.getName().startsWith("com.")) {
			Field[] supField = supClass.getDeclaredFields();
			loadReqParamField(bean, paramFields, supClass, supField);
		}
		if (fields != null && fields.length > 0) {
			for (Field field : fields) {
				if (DocUtils.isFieldHaveGetMethod(paramType, field)) {
					ParamField paramField = new ParamField();
					paramField.setName(field.getName());
					paramField.setType(field.getType().getSimpleName());
					if (MultipartFile.class.isAssignableFrom(field.getType())) {
						bean.setReqType("multipart/form-data");
					}
					AutoDocField adf = field.getAnnotation(AutoDocField.class);
					if (adf != null) {
						paramField.setNote(adf.value());
						paramField.setRequired(adf.required());
					} else {
						paramField.setNote("");
					}
					paramFields.add(paramField);
				}
			}
		}
	}

	private void buildRespParameters(Method method, DocBean bean) {
		// 返回参数
		Class<?> returnType = method.getReturnType();
		Type type = method.getGenericReturnType();
		ParamBean responseBean = new ParamBean();
		responseBean.setBeanName("");
		responseBean.setClassName(returnType.getSimpleName());
		loadParamField(returnType, responseBean, type);
		bean.setResponse(responseBean);
	}

	private void loadParamField(Class<?> rootClass, ParamBean paramBean, Type type) {
		List<ParamField> params = paramBean.getParams();
		List<ParamBean> subBeans = paramBean.getSubBeans();
		if (null == params) {
			params = new ArrayList<ParamField>();
			paramBean.setParams(params);
		}
		if (null == subBeans) {
			subBeans = new ArrayList<ParamBean>(3);
			paramBean.setSubBeans(subBeans);
		}

		Field[] rfs = rootClass.getDeclaredFields();
		buildParamField(rootClass, type, params, subBeans, rfs);

		if (StringUtils.isBlank(paramBean.getClassName())) {
			paramBean.setClassName(rootClass.getSimpleName());
		}

		Class<?> supClass = rootClass.getSuperclass();
		if (supClass != null && supClass.getName().startsWith("com.")) {
			loadParamField(supClass, paramBean, type);
		}
	}

	private void buildParamField(Class<?> rootClass, Type type,
			List<ParamField> params, List<ParamBean> subBeans, Field[] rfs) {
		if (rfs != null && rfs.length > 0) {
			for (Field rf : rfs) {
				// 需过滤不是返回的字段，如果字段又为list，再提取子字段
				if (DocUtils.isFieldHaveGetMethod(rootClass, rf)) {
					Type rfGenericType = rf.getGenericType();
					Class<?> clazz = rf.getType();
					// 字段是否是list类型 or set类型
					if (List.class.isAssignableFrom(rf.getType()) || Set.class.isAssignableFrom(rf.getType())) {
						String collectionType = rf.getType().getSimpleName();// 字段list类型的要加上list<xxx>
						if (rfGenericType != null && rfGenericType instanceof ParameterizedType) {
							ParameterizedType pt = (ParameterizedType) rfGenericType;
							Type tempType = pt.getActualTypeArguments()[0];
							if (tempType.toString().equals("T") && type != null) {
								tempType = ((ParameterizedType) type).getActualTypeArguments()[0];
							}
							if (!tempType.toString().equals("T") && tempType instanceof Class) {
								Class<?> genericClazz = (Class<?>) tempType;// 得到参数泛型的第一个参数的class
								ParamBean subBean = loadSubBean(rootClass, type, rf, collectionType, genericClazz);
								if (subBean != null) {
									subBeans.add(subBean);
								}

							} else if (tempType instanceof ParameterizedType) {
								Type[] actualTypes = ((ParameterizedType) tempType).getActualTypeArguments();
								Class<?> genericClazz = (Class<?>) actualTypes[0];// 最多嵌套一层List
								collectionType = collectionType + "<"
										+ ((Class<?>) ((ParameterizedType) tempType).getRawType()).getSimpleName();
								if (genericClazz instanceof Class) {
									ParamBean subBean = loadSubBean(rootClass, type, rf, collectionType, genericClazz);
									if (subBean != null) {
										subBeans.add(subBean);
									}
								}
							}
						}
					} else if (rf.getType().getName().startsWith("com.")) {
						Type temp = null;
						if (rfGenericType != null && rfGenericType instanceof ParameterizedType) {
							ParameterizedType pt = (ParameterizedType) rfGenericType;
							Type tempType = pt.getActualTypeArguments()[0];
							if (!tempType.toString().equals("T") && tempType instanceof Class) {
								temp = pt;
							} else if (tempType instanceof ParameterizedType) {
								temp = pt;
							} else {
								temp = type;
							}
						} else {
							temp = type;
						}
						Class<?> genericClazz = rf.getType();
						ParamBean subBean = loadSubBean(rootClass, temp, rf, null, genericClazz);
						if (subBean != null) {
							subBeans.add(subBean);
						}
					} else {// 普通字段

						if (rfGenericType.toString().equals("T") && type != null) {
							try {
								Type[] actualTypes = ((ParameterizedType) type).getActualTypeArguments();
								if (actualTypes[0] instanceof ParameterizedType) {
									Class<?> rawClass = (Class<?>) ((ParameterizedType) actualTypes[0]).getRawType();
									// 字段是否是list类型 or set类型
									if (List.class.isAssignableFrom(rawClass) || Set.class.isAssignableFrom(rawClass)) {
										String collectionType = rawClass.getSimpleName();// 字段list类型的要加上list<xxx>

										if (actualTypes[0] != null && actualTypes[0] instanceof ParameterizedType) {
											ParameterizedType pt = (ParameterizedType) actualTypes[0];
											Type tempType = pt.getActualTypeArguments()[0];
											if (tempType instanceof Class) {
												Class<?> genericClazz = (Class<?>) tempType;// 得到参数泛型的第一个参数的class

												ParamBean subBean = loadSubBean(rootClass, type, rf, collectionType,
														genericClazz);
												if (subBean != null) {
													subBeans.add(subBean);
												}
											}

										}
									}else {
										//分页对象
										ParamBean subBean = loadSubBean(rootClass, actualTypes[0], rf, null, rawClass);
										if (subBean != null) {
											subBeans.add(subBean);
										}
//									ParamBean subBean = loadSubBean(rawClass, type, fields, rawClass.getSimpleName(),
//											genericClazz);
//									buildRespField(rawClass, actualTypes[0], params, subBeans, fields);
									}
								} else if (actualTypes[0] instanceof Class) {
									Class<?> genericClazz = (Class<?>) actualTypes[0];
									ParamBean subBean = loadSubBean(rootClass, type, rf, null, genericClazz);
									if (subBean != null) {
										subBeans.add(subBean);
									}
								}
							} catch (Exception e) {
								
							}
						} else {
							ParamField pf = new ParamField();
							pf.setName(rf.getName());
							pf.setType(rf.getType().getSimpleName());

							AutoDocField adField = rf.getAnnotation(AutoDocField.class);
							if (null != adField) {
								pf.setNote(adField.value());
								pf.setRequired(adField.required());
							} else {
								pf.setNote("");
							}
							params.add(pf);
						}
					}
				}
			}
		}
	}

	private ParamBean loadSubBean(Class<?> rootClass, Type type, Field rf, String collectionType,
			Class<?> genericClazz) {
		if (!genericClazz.isAssignableFrom(rootClass)) {// 加此条件防止死循环递归（子bean是自己类型）
			ParamBean subBean = new ParamBean();
			subBean.setBeanName(rf.getName());// 字段名称
			AutoDocField docField = rf.getAnnotation(AutoDocField.class);
			if (docField != null) {
				subBean.setNote(docField.value());
				subBean.setRequired(docField.required());
			}
			if (collectionType != null) {
				subBean.setClassName(collectionType + "<" + genericClazz.getSimpleName() + ">");
			}
			loadParamField(genericClazz, subBean, type);// 递归
			return subBean;
		}
		return null;
	}

}

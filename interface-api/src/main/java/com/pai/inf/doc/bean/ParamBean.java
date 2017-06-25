package com.pai.inf.doc.bean;

import java.util.List;

public class ParamBean {
	private String beanName; // 这个bean的名称
	private String className;
	private List<ParamField> params; // 参数[类型，字段名称]
	private List<ParamBean> subBeans; // 嵌套的list
	private String note;
	private boolean required;

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		print(sb, this, "");

		return sb.toString();
	}

	private void print(StringBuffer sb, ParamBean bean, String s) {

		String space = "    ";

		if (bean.isRequired()) {
			sb.append("*");
		} else {
			sb.append(" ");
		}

		sb.append(s + bean.getBeanName());
		sb.append("<" + bean.getClassName() + ">");
		sb.append("{ : ");
		if (bean.getNote() != null) {
			sb.append(bean.getNote());
		}
		sb.append("\n");
		if (null != bean.getParams() && bean.getParams().size() > 0) {
			for (ParamField f : bean.getParams()) {
				sb.append(s + space);
				if (f.isRequired()) {
					sb.append("*");
				} else {
					sb.append(" ");
				}
				sb.append(f.getName());
				sb.append("<" + f.getType() + ">");
				sb.append(" : " + f.getNote() + "\n");
			}
		}

		// 递归
		if (bean.getSubBeans() != null && bean.getSubBeans().size() > 0) {
			for (ParamBean subBean : bean.getSubBeans()) {
				print(sb, subBean, s + space);
				sb.append("\n");
			}
		}
		sb.append(" ");
		sb.append(s + "}");
	}

	public String getBeanName() {
		return beanName;
	}

	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public List<ParamField> getParams() {
		return params;
	}

	public void setParams(List<ParamField> params) {
		this.params = params;
	}

	public List<ParamBean> getSubBeans() {
		return subBeans;
	}

	public void setSubBeans(List<ParamBean> subBeans) {
		this.subBeans = subBeans;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

}

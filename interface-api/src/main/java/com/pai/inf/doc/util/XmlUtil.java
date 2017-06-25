package com.pai.inf.doc.util;

import java.io.File;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

/**
 * 操作xml的工具
 */
public class XmlUtil {
	public static void main(String[] args) {
		System.out.println(getVersion());
	}

	/**
	 * 获取项目的版本号
	 * 
	 */
	public static String getVersion() {
		try {
			SAXBuilder builder = new SAXBuilder();
			Document document = builder.build(new File("pom.xml"));

			Element root = document.getRootElement();

			List<Element> list = root.getChildren();
			for (Element e : list) {
				if ("version".equals(e.getName()))
					return e.getText();
			}
			//如果取不到取parent
			for (Element e : list) {
				if ("parent".equals(e.getName())){
					List<Element> elements = e.getChildren();
					for (Element element : elements) {
						if ("version".equals(element.getName()))
							return element.getText();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "1";
	}

	public static String getProject() {
		try {
			SAXBuilder builder = new SAXBuilder();
			Document document = builder.build(new File("pom.xml"));

			Element root = document.getRootElement();

			List<Element> list = root.getChildren();
			for (Element e : list) {
				if ("artifactId".equals(e.getName())) {
					return e.getText();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "主模块";
	}

}

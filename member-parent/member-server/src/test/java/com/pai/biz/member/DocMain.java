package com.pai.biz.member;

import com.pai.base.api.doc.bean.ConfigBean;
import com.pai.base.api.doc.constants.VersionType;
import com.pai.base.api.doc.util.DocHelper;

public class DocMain {

	public static void main(String[] args) throws Exception {
		DocHelper helper = new DocHelper(); // 文档创建工具
		helper.createDoc(buildConfigBean());
	}

	private static ConfigBean buildConfigBean() {
		String filePath = "D:/会员RPC全量接口文档.pdf";
		ConfigBean configBean = new ConfigBean();
		// 设置当前的项目版本
		configBean.setCurrentVersion(VersionType.V100);
		// 设置生成的文档保存路径
		configBean.setFilePath(filePath);
		// 设置是否显示deprecated的方法
		configBean.setShowDeprecated(true);
		// 设置是否生成所有版本，默认为当前版本
		configBean.setShowAllVersion(true);
		// 设置服务的类型是否为RPC，默认为WEB
		configBean.setRpcServer(true);
		// 设置URL前缀
		configBean.setPrefix("");
		return configBean;
	}

}

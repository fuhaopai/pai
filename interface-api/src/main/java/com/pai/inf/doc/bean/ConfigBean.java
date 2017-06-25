package com.pai.inf.doc.bean;

import com.pai.inf.doc.constants.VersionType;

public class ConfigBean {

	private String filePath;

	private VersionType currentVersion;

	private boolean showAllVersion = false;

	private boolean showDeprecated = false;

	private boolean rpcServer = false;
	
	private String prefix;

	public ConfigBean() {
		super();
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public VersionType getCurrentVersion() {
		return currentVersion;
	}

	public void setCurrentVersion(VersionType currentVersion) {
		this.currentVersion = currentVersion;
	}

	public boolean isShowAllVersion() {
		return showAllVersion;
	}

	public void setShowAllVersion(boolean showAllVersion) {
		this.showAllVersion = showAllVersion;
	}

	public boolean isShowDeprecated() {
		return showDeprecated;
	}

	public void setShowDeprecated(boolean showDeprecated) {
		this.showDeprecated = showDeprecated;
	}

	public boolean isRpcServer() {
		return rpcServer;
	}

	public void setRpcServer(boolean rpcServer) {
		this.rpcServer = rpcServer;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

}

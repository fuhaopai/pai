package com.pai.service.quartz;

/**
 * 
 * <pre> 
 * 描述：供应用启动时，根据持久化的定时任务进行任务启动使用。
 * 构建组：service-quartz
 * 作者：winston
 * 邮箱: yanchaomin@skg.com
 * 日期:Mar 25, 2015-9:18:52 AM
 * 版权：SKG 公司版权所有
 * </pre>
 */
public interface SchedulerStartupRegister {
	/**
	 * 将该应用下面的所有任务加到容器里，根据状态进行处理
	 * @param group 
	 * void
	 * @exception 
	 * @since  1.0.0
	 */
	public void registerJobs(String group);
}

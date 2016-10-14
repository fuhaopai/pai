package com.pai.service.quartz;

import java.util.List;

import com.pai.service.quartz.entity.IJobDefPo;
import com.pai.service.quartz.entity.IJobParamPo;

/**
 * <pre> 
 * 描述：本模块需要的持久化支持
 * 构建组：service-quartz
 * 作者：winston
 * 邮箱: yanchaomin@skg.com
 * 日期:Mar 21, 2015-6:32:07 PM
 * 版权：SKG 公司版权所有
 * </pre>
 */
public interface JobPersistenceSupport {
	/**
	 * 更新该计划的最后执行时间。
	 * @param jobDefId
	 * void
	 * @exception 
	 * @since  1.0.0
	 */
	public void updateLastRunTime(String jobDefId);

	
	/**
	 * 查询所有激活的任务，根据组值。
	 * @param group
	 * @return 
	 * List<IJobDefPo>
	 * @exception 
	 * @since  1.0.0
	 */
	public List<IJobDefPo> findActivedJobDefPos(String group);
	
	public IJobDefPo getJobDefPo(String jobDefId);
	
	/**
	 * 查询该任务的默认参数列表
	 * @param jobDef
	 * @return 
	 * List<IJobParam>
	 * @exception 
	 * @since  1.0.0
	 */
	public List<IJobParamPo> findParams(String jobDefId);
	
	/**
	 * 保存执行历史到相应的数据表中
	 * @param jobId
	 * @param status
	 * @param log 
	 * void
	 * @exception 
	 * @since  1.0.0
	 */
	public void saveRunHistory(String key,String group,String status,String log);
}

package com.pai.base.db.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.pai.base.api.service.IdGenerator;
import com.pai.base.core.util.string.StringUtils;
import com.pai.service.redis.JedisUtil;
import com.pai.service.redis.RedisDb;

/**
 * 主键生成策略：
 * 	  假设4台数据库，第一台mysql主键从1开始每次加4，第二台从2开始每次加4，以此类推。。
 * 	  目前只采用redis库和db库，redis的基数是1，数据库的基数是2，增长数为2，之后分布式分库处理改基数和增长数
 * 	 reids失效后会从数据库中拿主键，id=max_num+incr_num，
 *   恢复从redis中获取主键要恢复redis主键最大值
 */
public class IdGeneratorImpl implements IdGenerator,InitializingBean{

		@Resource
		private JdbcTemplate jdbcTemplate;
	    /**
	     * 机器ID
	     */
	    private Long machineId;
	    /**
	     * 机器名称 多台物理机器集群部署时，需要唯一区分
	     */
	    private String machineName;
	    /**
	     * id增长间隔
	     */
	    private Integer incrBy;
	    /**
	     * 数据库id基数
	     */
	    private Integer baseNum = 1;
	    
	    @SuppressWarnings({ "unchecked", "rawtypes" })
		public String genSid() {
	    	String idString = null;
			if(machineId!=null && StringUtils.isNotEmpty(machineName) && incrBy != null){
				try {
					idString = JedisUtil.getInstance().incrBy(machineName, incrBy, RedisDb.DBFIFTEEN.getDb());
					if(StringUtils.isEmpty(idString)){
				        try {
				        	String updateSql = "UPDATE pai_common_id SET max_num=max_num+incr_num, SET update_time="+new Date()+" WHERE id_=? AND name=?";
					        jdbcTemplate.update(updateSql, machineId, machineName);
					        String querySql = "select max_num from pai_common_id where id_ ="+machineId+" and name='"+ machineName+ "'";
					        jdbcTemplate.queryForObject(querySql, new RowMapper() {
				                public Object mapRow(ResultSet rs, int i) throws SQLException {
				                    return rs.getString("max_num");
				                }
				            });
				        } catch (Exception ex) {	            
				            String insertSql = "INSERT INTO pai_common_id(id_,name,base_num,incr_num,max_num,update_time)VALUES(?,?,?,?,?,?)";
				            jdbcTemplate.update(insertSql, new Object[]{machineId, machineName, baseNum, incrBy, baseNum, new Date()});
				            return String.valueOf(baseNum);
				        }
					}
				} catch (Exception e) {
					//后台显著位置显示
					try {
			        	String updateSql = "UPDATE pai_common_id SET max_num=max_num+incr_num, update_time="+new Date()+" WHERE id_=? AND name=?";
				        jdbcTemplate.update(updateSql, machineId, machineName);
				        String querySql = "select max_num from pai_common_id where id_ ="+machineId+" and name='"+ machineName+ "'";
				        jdbcTemplate.queryForObject(querySql, new RowMapper() {
			                public Object mapRow(ResultSet rs, int i) throws SQLException {
			                    return rs.getString("max_num");
			                }
			            });
			        } catch (Exception ex) {	            
			            String insertSql = "INSERT INTO pai_common_id(id_,name,base_num,incr_num,max_num,update_time)VALUES(?,?,?,?,?,?)";
			            jdbcTemplate.update(insertSql, new Object[]{machineId, machineName, baseNum, incrBy, baseNum, new Date()});
			            return String.valueOf(baseNum);
			        }
				}
			}
	        return idString;
	    }

	    public void afterPropertiesSet() throws Exception {
	    	
	    }

		public Long getMachineId() {
			return machineId;
		}

		public void setMachineId(Long machineId) {
			this.machineId = machineId;
		}

		public String getMachineName() {
			return machineName;
		}

		public void setMachineName(String machineName) {
			this.machineName = machineName;
		}

		public Integer getIncrBy() {
			return incrBy;
		}

		public void setIncrBy(Integer incrBy) {
			this.incrBy = incrBy;
		}
	    
}

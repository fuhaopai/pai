package com.pai.base.db.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.pai.base.api.service.IdGenerator;
import com.pai.base.core.util.string.StringUtils;
import com.pai.service.redis.RedisUtil;

public class IdGeneratorImpl implements IdGenerator,InitializingBean{

	@Resource
	public JdbcTemplate jdbcTemplate;
       //判断初始化状态，modify by Suoron on 2015-08-17
	    private static int init_status = 0;	
	    /**
	     * 增长段值
	     */
	    private final Long increaseBound = 1000000l;
	    /**
	     * 当前DBID
	     */
	    private Long dbid = 1l;
	    /**
	     * 当前递增的最大值
	     */
	    private Long maxDbid = -1l;
	    /**
	     * 机器ID
	     */
	    private Long machineId = null;
	    /**
	     * 机器名称 多台物理机器集群部署时，需要唯一区分
	     */
	    private String machineName="";
	    
	    /**
	     * ID的基准长度
	     */
	    private Long idBase=10000000000000L;

		public Long genLid() {
			check_init_stat();
	        return getUniqueId();
	    }

	    public String genSid() {
	        return genLid().toString();
	    }
	    
		public String genUuid() {
	    	return UUID.randomUUID().toString();
		}

		/**
	     * 获取唯一的ID标识
	     *
	     * @return
	     */
	    private synchronized Long getUniqueId() {
	        if (dbid > maxDbid) {
	            genNextDbIds();
	        }
	        Long nextId = dbid++;
	        return nextId + machineId * idBase;
	    }
		/**
	     * 初始化检查
	     *
	     * @return
	     */				
       private synchronized void check_init_stat(){
			if(init_status == 0){
				init_status = 1;
				init();			
				init_redis_id();
			}    	   
        }
	    private void genNextDbIds() {
	        String sql = "UPDATE gebi_gl_id SET start_=?,max_=? WHERE id_=?";
	        dbid = maxDbid;
	        maxDbid += increaseBound;
	        jdbcTemplate.update(sql, dbid, maxDbid, machineId);
	    }
	    
	    public Long genIncrId(String key,int min_value){					
	    	check_init_stat();
		    return RedisUtil.Singleton.getInstance().makeId(key, min_value);	    	
	    }
	    
	    @SuppressWarnings({ "unchecked", "rawtypes" })
		public void init(){
	    	if(machineId!=null && StringUtils.isNotEmpty(machineName)){
		        String sql = "select * from gebi_gl_id where id_ ="+machineId+" and mac_name_='"+ machineName+ "'";
		        //检查该机器是否已经存在增长的键值记录
		        try {
		            jdbcTemplate.queryForObject(sql, new RowMapper() {
		                public Object mapRow(ResultSet rs, int i) throws SQLException {
		                    dbid = rs.getLong("start_");
		                    maxDbid = rs.getLong("max_");
		                    machineId = rs.getLong("id_");
		                    return machineId;
		                }
		            });
		            //插入该机器的键值增长记录
		            genNextDbIds();
		        } catch (Exception ex) {	            
		            maxDbid = dbid + increaseBound;
		            sql = "INSERT INTO gebi_gl_id(id_,start_,max_,mac_name_)VALUES(?,?,?,?)";
		            jdbcTemplate.update(sql, new Object[]{machineId.toString(), dbid, maxDbid, machineName});
		        }
	    	}
	    }
	    
	    @SuppressWarnings({ "unchecked", "rawtypes" })
		public void init_redis_id(){
	        String sql = "select * from gebi_gl_id where type_='redis'";
	        try {
	            jdbcTemplate.query(sql, new RowMapper() {
	                public Object mapRow(ResultSet rs, int i) throws SQLException {	      
	                	//System.out.println("Init Redis increment Key:"+rs.getString("redis_key_")+":"+rs.getInt("curr_value_"));
	                    return genIncrId(rs.getString("redis_key_"),rs.getInt("curr_value_")); 
	                }
	            });
	        } catch (Exception ex) {
	        }
	    }
	    public void afterPropertiesSet() throws Exception {
            //init();	        
	    }

		public void setMachineId(Long machineId) {
			this.machineId = machineId;
		}

		public void setMachineName(String machineName) {
			this.machineName = machineName;
		}

}

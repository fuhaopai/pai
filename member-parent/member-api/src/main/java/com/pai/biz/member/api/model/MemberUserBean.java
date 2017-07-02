package com.pai.biz.member.api.model;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pai.base.api.doc.annotation.AutoDocField;
import com.pai.base.api.entity.Bean;

/**
 * 对象功能:会员表 Tbl对象
 * 开发公司:π
 * 开发人员:FU_HAO
 * 创建时间:2017-07-02 18:04:25
 */
public class MemberUserBean extends Bean{
	
	private static final long serialVersionUID = 2L;
	
	@AutoDocField("会员Id")
	private String  id; 		/*会员Id*/
	@AutoDocField("昵称")
	private String  name; 		/*昵称*/
	@AutoDocField("手机号")
	private String  phone; 		/*手机号*/
	@AutoDocField("密码")
	private String  password; 		/*密码*/
	@AutoDocField("邮箱")
	private String  mail; 		/*邮箱*/
	@AutoDocField("头像")
	private String  profile; 		/*头像*/
	@AutoDocField("行业")
	private String  vocation; 		/*行业*/
	@AutoDocField("学校")
	private String  school; 		/*学校*/
	@AutoDocField("专业")
	private String  profession; 		/*专业*/
	@AutoDocField("状态（1=正常；2=冻结）")
	private Integer  status; 		/*状态（1=正常；2=冻结）*/
	@AutoDocField("性别（男，女）")
	private char  gender; 		/*性别（男，女）*/
	@AutoDocField("密码后缀")
	private String  suffix; 		/*密码后缀*/
	@AutoDocField("个人简介")
	private String  description; 		/*个人简介*/
	@AutoDocField("昵称状态（1=可修改；2=不可修改）")
	private Integer  nameStatus; 		/*昵称状态（1=可修改；2=不可修改）*/
	@AutoDocField("上次昵称修改时间")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	private java.util.Date  nameUpdateTime; 		/*上次昵称修改时间*/
	@AutoDocField("匿名用户对应id")
	private String  fameId; 		/*匿名用户对应id*/
	@AutoDocField("创建人")
	private String  createBy; 		/*创建人*/
	@AutoDocField("创建时间")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	private java.util.Date  createTime; 		/*创建时间*/
	@AutoDocField("修改人")
	private String  updateBy; 		/*修改人*/
	@AutoDocField("修改时间")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	private java.util.Date  updateTime; 		/*修改时间*/
	
	
	public void setId(String id) 
	{
		this.id = id;
	}
	/**
	 * 返回 会员Id
	 * @return
	 */
	public String getId() 
	{
		return this.id;
	}
	public void setName(String name) 
	{
		this.name = name;
	}
	/**
	 * 返回 昵称
	 * @return
	 */
	public String getName() 
	{
		return this.name;
	}
	public void setPhone(String phone) 
	{
		this.phone = phone;
	}
	/**
	 * 返回 手机号
	 * @return
	 */
	public String getPhone() 
	{
		return this.phone;
	}
	public void setPassword(String password) 
	{
		this.password = password;
	}
	/**
	 * 返回 密码
	 * @return
	 */
	public String getPassword() 
	{
		return this.password;
	}
	public void setMail(String mail) 
	{
		this.mail = mail;
	}
	/**
	 * 返回 邮箱
	 * @return
	 */
	public String getMail() 
	{
		return this.mail;
	}
	public void setProfile(String profile) 
	{
		this.profile = profile;
	}
	/**
	 * 返回 头像
	 * @return
	 */
	public String getProfile() 
	{
		return this.profile;
	}
	public void setVocation(String vocation) 
	{
		this.vocation = vocation;
	}
	/**
	 * 返回 行业
	 * @return
	 */
	public String getVocation() 
	{
		return this.vocation;
	}
	public void setSchool(String school) 
	{
		this.school = school;
	}
	/**
	 * 返回 学校
	 * @return
	 */
	public String getSchool() 
	{
		return this.school;
	}
	public void setProfession(String profession) 
	{
		this.profession = profession;
	}
	/**
	 * 返回 专业
	 * @return
	 */
	public String getProfession() 
	{
		return this.profession;
	}
	public void setStatus(Integer status) 
	{
		this.status = status;
	}
	/**
	 * 返回 状态（1=正常；2=冻结）
	 * @return
	 */
	public Integer getStatus() 
	{
		return this.status;
	}
	public void setGender(char gender) 
	{
		this.gender = gender;
	}
	/**
	 * 返回 性别（男，女）
	 * @return
	 */
	public char getGender() 
	{
		return this.gender;
	}
	public void setSuffix(String suffix) 
	{
		this.suffix = suffix;
	}
	/**
	 * 返回 密码后缀
	 * @return
	 */
	public String getSuffix() 
	{
		return this.suffix;
	}
	public void setDescription(String description) 
	{
		this.description = description;
	}
	/**
	 * 返回 个人简介
	 * @return
	 */
	public String getDescription() 
	{
		return this.description;
	}
	public void setNameStatus(Integer nameStatus) 
	{
		this.nameStatus = nameStatus;
	}
	/**
	 * 返回 昵称状态（1=可修改；2=不可修改）
	 * @return
	 */
	public Integer getNameStatus() 
	{
		return this.nameStatus;
	}
	public void setNameUpdateTime(java.util.Date nameUpdateTime) 
	{
		this.nameUpdateTime = nameUpdateTime;
	}
	/**
	 * 返回 上次昵称修改时间
	 * @return
	 */
	public java.util.Date getNameUpdateTime() 
	{
		return this.nameUpdateTime;
	}
	public void setFameId(String fameId) 
	{
		this.fameId = fameId;
	}
	/**
	 * 返回 匿名用户对应id
	 * @return
	 */
	public String getFameId() 
	{
		return this.fameId;
	}
	public void setCreateBy(String createBy) 
	{
		this.createBy = createBy;
	}
	/**
	 * 返回 创建人
	 * @return
	 */
	public String getCreateBy() 
	{
		return this.createBy;
	}
	public void setCreateTime(java.util.Date createTime) 
	{
		this.createTime = createTime;
	}
	/**
	 * 返回 创建时间
	 * @return
	 */
	public java.util.Date getCreateTime() 
	{
		return this.createTime;
	}
	public void setUpdateBy(String updateBy) 
	{
		this.updateBy = updateBy;
	}
	/**
	 * 返回 修改人
	 * @return
	 */
	public String getUpdateBy() 
	{
		return this.updateBy;
	}
	public void setUpdateTime(java.util.Date updateTime) 
	{
		this.updateTime = updateTime;
	}
	/**
	 * 返回 修改时间
	 * @return
	 */
	public java.util.Date getUpdateTime() 
	{
		return this.updateTime;
	}
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() 
	{
		return new ToStringBuilder(this)
		.append("id", this.id) 
		.append("name", this.name) 
		.append("phone", this.phone) 
		.append("password", this.password) 
		.append("mail", this.mail) 
		.append("profile", this.profile) 
		.append("vocation", this.vocation) 
		.append("school", this.school) 
		.append("profession", this.profession) 
		.append("status", this.status) 
		.append("gender", this.gender) 
		.append("suffix", this.suffix) 
		.append("description", this.description) 
		.append("nameStatus", this.nameStatus) 
		.append("nameUpdateTime", this.nameUpdateTime) 
		.append("fameId", this.fameId) 
		.append("createBy", this.createBy) 
		.append("createTime", this.createTime) 
		.append("updateBy", this.updateBy) 
		.append("updateTime", this.updateTime) 
		.toString();
	}
}
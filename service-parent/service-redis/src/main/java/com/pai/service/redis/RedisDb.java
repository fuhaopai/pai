package com.pai.service.redis;

public enum RedisDb {
	DBZERO(0),  //会员信息
	DBONE(1),	//帖子库
	DBTWO(2),	//专栏库
	DBTHREE(3),
	DBFOUR(4),
	DBFIVE(5),
	DBSIX(6),
	DBSEVEN(7),
	DBEIGHT(8),
	DBNINE(9),
	DBTEN(10),
	DBELEVEN(11),
	DBTWELVE(12),
	DBTHIRTEEN(13),
	DBFOURTEEN(14),
	DBFIFTEEN(15); //表id,万万不能清除
	
	private RedisDb(int db) {
		this.db = db;
	}

	private int db;

	public int getDb() {
		return db;
	}

	public void setDb(int db) {
		this.db = db;
	}
	
}

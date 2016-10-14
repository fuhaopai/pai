package com.pai.service.redis.entity;

public class pairVo<K,V> {
	
	private K key;
	private V value;
	  
	public K getKey() {
		return key;
	}
	public void setKey(K key) {
		this.key = key;
	}
	public V getValue() {
		return value;
	}
	public void setValue(V value) {
		this.value = value;
	}       
	
	public pairVo(K key, V value){
		this.key = key;
		this.value = value;
	}
	
    public pairVo makePair(K key, V value) {  
        return new pairVo(key, value);  
    } 
}

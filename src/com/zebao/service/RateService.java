package com.zebao.service;

import java.util.Map;

public interface RateService {
	/*
	 * 从mongodb中读取
	 */
	public String read();
	/*
	 * 写入mongodb
	 */
	public void save(Map map,String id);
	/*
	 * 删除所有表
	 */
	public void dropAll();
	/*
	 * 更新指定表
	 */
	public void replace(String id,Map map);
	/*
	 * 删除指定表
	 */
	public void deleteById(String id);
}

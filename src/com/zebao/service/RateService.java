package com.zebao.service;

import java.util.Map;

public interface RateService {
	/*
	 * ��mongodb�ж�ȡ
	 */
	public String read();
	/*
	 * д��mongodb
	 */
	public void save(Map map,String id);
	/*
	 * ɾ�����б�
	 */
	public void dropAll();
	/*
	 * ����ָ����
	 */
	public void replace(String id,Map map);
	/*
	 * ɾ��ָ����
	 */
	public void deleteById(String id);
}

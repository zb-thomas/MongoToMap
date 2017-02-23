package com.zebao.action;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonAppend.Prop;
import com.zebao.service.ExcelService;
import com.zebao.service.RateService;
import com.zebao.service.impl.*;

public class MongoAction {
	public static void main(String[] args) {
		
		MongoAction ma = new MongoAction();
//		ma.saveAll();
		ObjectMapper mapper = new ObjectMapper();
		try {
			@SuppressWarnings("unchecked")
			Map<String,Map<String,Map<String,Map<String,Double>>>> map =mapper.readValue(ma.readAll(), Map.class);
//			for(int i = 0;i<10;i++){
//				System.out.println(map.get("1").get("male").get("5").get(i+"").equals(map.get("2").get("male").get("5").get(i+"")));
//			}
			System.out.println(map.get("1").get("male").get("5").get("10"));
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	/*
	 * 读取map
	public static void readMap(Map map){
		Set<Entry> entrySet = map.entrySet();
		for (Entry entry:entrySet){
			if(entry.getValue() instanceof Map){
				readMap((Map) entry.getValue());
			}
			System.out.print(entry.getKey()+"="+entry.getValue());
		}
	}
	*/
	private RateService rsi=new RateServiceImpl();
	private ExcelService esi = new ExcelServiceImpl();
	private Properties pro = new Properties();
	/**
	 * 存储所有的费率表,在储存之前会删除所有表
	 */
	public void saveAll() {

		drop();
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(
					"src\\com\\zebao\\action\\idaddress.properties"));
			pro.load(in);
			Iterator<String> it = pro.stringPropertyNames().iterator();
			while (it.hasNext()) {
				String id = it.next();
				System.out.println(pro.getProperty(id));
				String path = "D:\\ratetable\\" + pro.getProperty(id)+".xlsx";
				rsi.save(esi.readFromExcel(path), id);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 更新单个费率表
	 */
	public void replace(String id){
		
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(
					"src\\com\\zebao\\action\\idaddress.properties"));
			pro.load(in);
			String path ="D:\\ratetable\\"+ pro.getProperty(id)+".xlsx";
			rsi.replace(id,esi.readFromExcel(path));
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	/**
	 * 删除所有表
	 */
	public void drop(){
		
		rsi.dropAll();
	}
	/**
	 * 删除单个表
	 */
	public void deleteById(String id){
		
		rsi.deleteById(id);
	}
	/**
	 * 读取所有数据库内容
	 */
	public String readAll(){
		
		return rsi.read();
	}
}

package com.zebao.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zebao.dao.MongoDao;
import com.zebao.service.RateService;
public  class RateServiceImpl implements RateService {
	private MongoDao md = new MongoDao();;
	public String read() {
		return md.readAllMongo();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void save(Map map,String id) {
		try {
			md.saveToMongo(map,id);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void dropAll() {
		// TODO Auto-generated method stub
		md.drop();
	}

	@Override
	public void deleteById(String id) {
		// TODO Auto-generated method stub
		md.delete(id);
	}

	@Override
	public void replace(String id,Map map) {
		md.replace(id, map);
		
	}
	
}

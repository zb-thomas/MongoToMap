package com.zebao.service.impl;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.zebao.dao.ExcelDao;
import com.zebao.service.ExcelService;
public  class ExcelServiceImpl implements ExcelService {
	ExcelDao ed = new ExcelDao();
	public Map<String, Map<String, Map<String, Double>>> readFromExcel(String path) throws IOException {
		
		Map<String, Map<String, Map<String, Double>>> map = ed.getMap(path);
		return map;
	}

}

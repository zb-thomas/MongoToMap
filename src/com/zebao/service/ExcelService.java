package com.zebao.service;

import java.io.IOException;
import java.util.Map;

public interface ExcelService {
/**
 * ¥”excel÷–∂¡»°map
 * @throws IOException 
 */
	public Map<String, Map<String, Map<String, Double>>> readFromExcel(String path) throws IOException;
}

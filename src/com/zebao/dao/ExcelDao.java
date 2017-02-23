package com.zebao.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Repository;
public class ExcelDao {
	/**
	 * 读取Excel中内容为map
	 * 后期需要将map更换为map<id,Map<sex,Nap<type,Map<age,price>>>>的形式，前面要加一个id，对应不同的保险
	 * @return
	 * @throws IOException
	 */
	public Map<String, Map<String, Map<String, Double>>> getMap(String path)
			throws IOException {
		Map<String, Map<String, Map<String, Double>>> map = new HashMap<String, Map<String, Map<String, Double>>>();
		// 按照性别将map分类为male和female
		map.put("male", new HashMap<String, Map<String, Double>>());
		map.put("female", new HashMap<String, Map<String, Double>>());
		File file = new File(path);
		InputStream in = new FileInputStream(file);
		XSSFWorkbook excel = new XSSFWorkbook(in);
		XSSFSheet sheet = excel.getSheetAt(0);
		// 将分期数目存到list中方便调用
		List<String> list = new ArrayList<String>();
		// 获取总行数
		int rowNum = sheet.getLastRowNum();
		for (int j = 1; j <= rowNum; j++) {
			if (j == 1) {
				// 获取单元格数
				int cellNum = sheet.getRow(j).getLastCellNum();
				// 遍历单元格
				for (int k = 0; k < cellNum; k++) {
					String typeValue = null;
					// 获取单元格内内容，必须是数字才被list获取
					if (sheet.getRow(j).getCell(k).getCellType() == Cell.CELL_TYPE_NUMERIC) {
						typeValue = String.valueOf((int) sheet.getRow(j)
								.getCell(k).getNumericCellValue());
						list.add(typeValue);
						map.get("male").put(typeValue,
								new HashMap<String, Double>());
						map.get("female").put(typeValue,
								new HashMap<String, Double>());
					} else {
						typeValue = sheet.getRow(j).getCell(k)
								.getStringCellValue();
					}
				}
			} else if (j == 2) {
				continue;
			} else {
				String ageValue = null;
				// 获取单元格内内容
				if (sheet.getRow(j).getCell(0).getCellType() == Cell.CELL_TYPE_NUMERIC) {
					ageValue = String.valueOf((int) sheet.getRow(j).getCell(0)
							.getNumericCellValue());
				} else {
					ageValue = sheet.getRow(j).getCell(0).getStringCellValue();
				}
				// 每一行的第一列是Agevalue，按此分类
				int cellNum1 = sheet.getRow(j).getLastCellNum();
				for (int k = 0; k < cellNum1; k++) {
					// 金额
					double cellValue = 0;
					if (sheet.getRow(j).getCell(k).getCellType() == Cell.CELL_TYPE_BLANK) {
						continue;
					} else if (sheet.getRow(j).getCell(k).getCellType() == Cell.CELL_TYPE_NUMERIC) {
						cellValue = sheet.getRow(j).getCell(k)
								.getNumericCellValue();
					} else {
						cellValue = Double.parseDouble(sheet.getRow(j)
								.getCell(k).getStringCellValue());
					}
					// 插入数据
					if (k > 0 && k % 2 == 0) {
						map.get("female").get(list.get(k / 2 - 1))
								.put(ageValue, cellValue);
					}
					if (k > 0 && k % 2 != 0) {
						map.get("male").get(list.get((int) Math.ceil(k / 2)))
								.put(ageValue, cellValue);
					}

				}
			}
		}
		return map;
	}
}

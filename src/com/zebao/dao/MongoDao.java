package com.zebao.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.bson.Document;
import org.junit.Test;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.util.JSON;
import com.zebao.dao.util.MongoDaoClient;

public class MongoDao<T> {
	MongoDaoClient mdc = MongoDaoClient.getMongoDaoInstance();
	MongoClient mongoClient=mdc.getMongoClient();
	private MongoDatabase data=mongoClient.getDatabase("local");
	private MongoCollection<Document> table=data.getCollection("price");

	

	/*
	 * 写入表
	 */
	public void saveToMongo(Map map, String id) throws JsonProcessingException {
		Map tableMap = new HashMap();
		tableMap.put(id, map);
		// 增加rateId，方便后续删除
		tableMap.put("rateId", id);
		ObjectMapper mapper = new ObjectMapper();
		Document doc = new Document(tableMap);
		table.insertOne(doc);

	}

	// public String readFromMongo(Integer id) {
	// FindIterable<Document> findIterable = collection.find();
	// MongoCursor<Document> mongoCursor = findIterable.iterator();
	// String priceBuffer = null;
	// while (mongoCursor.hasNext()) {
	// priceBuffer = mongoCursor.next().toJson();
	// }
	// ObjectMapper om = new ObjectMapper();
	//
	// return null;
	// }
	/**
	 * 查询所有的表
	 * 
	 * @return
	 */
	public String readAllMongo() {
		FindIterable<Document> fit = table.find();
		MongoCursor<Document> cursor = fit.iterator();
		StringBuffer sb = new StringBuffer("");
		while (cursor.hasNext()) {
			Document d = cursor.next();
			// 去除_id和reatid项目
			d.remove("_id");
			d.remove("rateId");
			sb.append(d.toJson());
		}
		// 将所有的json拼接
		return sb.toString().replace("}{", ",");
	}

	/**
	 * 更新表
	 */
	public void replace(String id, Map map) {
		Map tableMap = new HashMap();
		tableMap.put(id, map);
		// 增加rateId，方便后续删除
		tableMap.put("rateId", id);
		System.out.println(new Document("rateId", id));
		table.replaceOne(new Document("rateId", id), new Document(tableMap));
	}

	/**
	 * 删除指定表
	 */
	public void delete(String id) {
		table.deleteOne(new Document("rateId", id));
	}

	/**
	 * 删除所有表
	 */
	public void drop() {
		table.drop();
	}
}

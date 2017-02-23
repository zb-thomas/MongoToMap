package com.zebao.dao.util;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoException;
import com.zebao.dao.MongoDao;

public class MongoDaoClient {
	private final static String HOST = "localhost";// 端口
	private final static int PORT = 27017;// 端口
	private final static int POOLSIZE = 100;// 连接数量
	private final static int BLOCKSIZE = 100; // 等待队列长度
	private MongoClient mongoClient=null;
	public MongoClient getMongoClient() {
		return mongoClient;
	}

	public void setMongoClient(MongoClient mongoClient) {
		this.mongoClient = mongoClient;
	}

	private MongoDaoClient() {
		if (mongoClient == null) {
			MongoClientOptions.Builder build = new MongoClientOptions.Builder();
			build.connectionsPerHost(POOLSIZE); // 与目标数据库能够建立的最大connection数量为50
			//build.autoConnectRetry(true); // 自动重连数据库启动
			build.threadsAllowedToBlockForConnectionMultiplier(BLOCKSIZE); // 如果当前所有的connection都在使用中，则每个connection上可以有50个线程排队等待
			/*
			 * 一个线程访问数据库的时候，在成功获取到一个可用数据库连接之前的最长等待时间为2分钟
			 * 这里比较危险，如果超过maxWaitTime都没有获取到这个连接的话，该线程就会抛出Exception
			 * 故这里设置的maxWaitTime应该足够大，以免由于排队线程过多造成的数据库访问失败
			 */
			build.maxWaitTime(1000 * 60 * 2);
			build.connectTimeout(1000 * 60 * 1); // 与数据库建立连接的timeout设置为1分钟

			MongoClientOptions myOptions = build.build();
			try {
				// 数据库连接实例
				mongoClient = new MongoClient("127.0.0.1", myOptions);
//			} catch (UnknownHostException e) {
//				e.printStackTrace();
			} catch (MongoException e) {
				e.printStackTrace();
			}

		}
	}

	private static final MongoDaoClient mongoDaoClient = new MongoDaoClient();

	/**
	 * 
	 * 方法名：getMongoDaoInstance 描述：单例的静态工厂方法
	 * 
	 * @return
	 */
	public static MongoDaoClient getMongoDaoInstance() {
		return mongoDaoClient;
	}
	
}

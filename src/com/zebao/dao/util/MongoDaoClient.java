package com.zebao.dao.util;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoException;
import com.zebao.dao.MongoDao;

public class MongoDaoClient {
	private final static String HOST = "localhost";// �˿�
	private final static int PORT = 27017;// �˿�
	private final static int POOLSIZE = 100;// ��������
	private final static int BLOCKSIZE = 100; // �ȴ����г���
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
			build.connectionsPerHost(POOLSIZE); // ��Ŀ�����ݿ��ܹ����������connection����Ϊ50
			//build.autoConnectRetry(true); // �Զ��������ݿ�����
			build.threadsAllowedToBlockForConnectionMultiplier(BLOCKSIZE); // �����ǰ���е�connection����ʹ���У���ÿ��connection�Ͽ�����50���߳��Ŷӵȴ�
			/*
			 * һ���̷߳������ݿ��ʱ���ڳɹ���ȡ��һ���������ݿ�����֮ǰ����ȴ�ʱ��Ϊ2����
			 * ����Ƚ�Σ�գ��������maxWaitTime��û�л�ȡ��������ӵĻ������߳̾ͻ��׳�Exception
			 * ���������õ�maxWaitTimeӦ���㹻�����������Ŷ��̹߳�����ɵ����ݿ����ʧ��
			 */
			build.maxWaitTime(1000 * 60 * 2);
			build.connectTimeout(1000 * 60 * 1); // �����ݿ⽨�����ӵ�timeout����Ϊ1����

			MongoClientOptions myOptions = build.build();
			try {
				// ���ݿ�����ʵ��
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
	 * ��������getMongoDaoInstance �����������ľ�̬��������
	 * 
	 * @return
	 */
	public static MongoDaoClient getMongoDaoInstance() {
		return mongoDaoClient;
	}
	
}

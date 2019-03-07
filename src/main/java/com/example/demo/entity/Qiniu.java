package com.example.demo.entity;

import com.qiniu.common.Zone;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.util.Auth;

/**
 *  七牛认证
 * @author Liure
 *
 */
public class Qiniu {

	private String ak;
	private String sk;
	
	public Qiniu(String ak,String sk) {
		this.ak = ak;
		this.sk = sk;
	}
	
	/**
	 * 七牛认证对象
	 * @return
	 */
	public Auth getAuth() {
		return Auth.create(ak, sk);
	}
	
	/**
	 * 管理对象
	 * @return
	 */
	public BucketManager getBucketManager() {
		//构造一个带指定Zone对象的配置类
		Configuration cfg = new Configuration(Zone.zone0());
		//...其他参数参考类注释
		Auth auth = this.getAuth();
		return new BucketManager(auth, cfg);
	}
	
}

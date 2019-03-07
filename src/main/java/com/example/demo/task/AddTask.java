package com.example.demo.task;

import com.example.demo.api.QUtils;
import com.example.demo.entity.Qiniu;

public class AddTask extends Thread{

	private Qiniu qiniu;
	private String bucket;
	private String prefix;

	public AddTask(Qiniu qiniu,String bucket,String prefix) {
		this.qiniu = qiniu;
		this.bucket = bucket;
		this.prefix = prefix;
	}
	
	@Override
	public void run() {
		QUtils.getBucketFileList(qiniu, bucket, prefix);
	}
}

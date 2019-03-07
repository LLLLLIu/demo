package com.example.demo.api;

import com.example.demo.entity.Qiniu;
import com.example.demo.queue.LinkQueue;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.model.BatchStatus;
import com.qiniu.storage.model.FileInfo;

/**
 * 七牛工具类
 * @author Liure
 *
 */
public class QUtils {
	
	public static LinkQueue<String> queue = new LinkQueue<>();
	
	/**
	 * 根据文件名前缀筛选文件
	 * @param qiniu 七牛对象
	 * @param bucket 空间名称
	 * @param prefix 文件前缀
	 */
	public static void getBucketFileList(Qiniu qiniu,String bucket,String prefix) {
		BucketManager bucketManager = qiniu.getBucketManager();
		//每次迭代的长度限制，最大1000，推荐值 1000
		int limit = 1000;
		//指定目录分隔符，列出所有公共前缀（模拟列出目录效果）。缺省值为空字符串
		String delimiter = "";
		//列举空间文件列表
		BucketManager.FileListIterator fileListIterator = bucketManager.createFileListIterator(bucket, prefix, limit, delimiter);
		while (fileListIterator.hasNext()) {
		    //处理获取的file list结果
		    FileInfo[] items = fileListIterator.next();
		    for (FileInfo item : items) {
		    	queue.enQueue(item.key);
//		    	System.out.println(item.key + "加入队列  执行线程：" + Thread.currentThread().getName());
		    }
		}
	}
	
	
	/**
	 * 批量请求的文件数量不得超过1000
	 * @param qiniu 七牛对象
	 * @param bucket 空间名称
	 * @param keys 最多数量1000个
	 */
	public static void batchDelFile(Qiniu qiniu,String bucket,String... keys) {
		BucketManager bucketManager = qiniu.getBucketManager();
		try {
		    BucketManager.BatchOperations batchOperations = new BucketManager.BatchOperations();
		    batchOperations.addDeleteOp(bucket, keys);
		    Response response = bucketManager.batch(batchOperations);
		    BatchStatus[] batchStatusList = response.jsonToObject(BatchStatus[].class);
		    for (int i = 0; i < keys.length; i++) {
		        BatchStatus status = batchStatusList[i];
		        String key = keys[i];
		        System.out.print(key + "\t");
		        if (status.code == 200) {
		            System.out.println("删除成功 执行线程：" + Thread.currentThread().getName());
		        } else {
		            System.out.println(status.data.error);
		        }
		    }
		} catch (QiniuException ex) {
		    System.err.println(ex.response.toString());
		}
	}
	
}

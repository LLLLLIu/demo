package com.example.demo.task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.example.demo.DemoApplication;
import com.example.demo.api.QUtils;
import com.example.demo.entity.Qiniu;

/**
 * 启用单独线程 执行删除任务
 * @author Liure
 *
 */
public class DelTask extends Thread {
	
	private Qiniu qiniu;
	private String bucket;
	private boolean flag = true;

	public DelTask(Qiniu qiniu,String bucket) {
		this.qiniu = qiniu;
		this.bucket = bucket;
	}
	
	@Override
	public void run() {
		while(flag) {
			List<String> list1000 = new ArrayList<String>(1000);
			try {
				this.sleep(Long.valueOf(DemoApplication.props.getProperty("qiniu.sleep")));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				System.out.println("异常");
			}
			if(QUtils.queue.size() > 0)
			{
				do {
					list1000.add(QUtils.queue.deQueue());
				}while(list1000.size() < 1000 && QUtils.queue.size() > 0);
				String[] array = new String[list1000.size()];
				Integer operational = Integer.valueOf(DemoApplication.props.getProperty("qiniu.operational"));
				if(list1000.size() <= 1000) {
					if(operational == 0) {//删除
						QUtils.batchDelFile(qiniu, bucket, list1000.toArray(array));
					}else if(operational == 1){// 下载
						String domainOfBucket = DemoApplication.props.getProperty("qiniu.domainOfBucket");
						Integer download = Integer.valueOf(DemoApplication.props.getProperty("qiniu.download"));
						if(download == 1) {// 私有空间
							QUtils.downloadPrivateSource(qiniu, domainOfBucket, list1000.toArray(array));
						}else { // 公用空间
							QUtils.downloadOpenSource(qiniu, domainOfBucket , list1000.toArray(array));
						}
					}else {
						// 没有这个操作
						flag = false;
					}
				}
			}else {
				flag = false;
			}
		}
	}
}

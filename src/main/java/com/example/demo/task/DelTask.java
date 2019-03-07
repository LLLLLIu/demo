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
				String[] array = new String[1000];
				if(list1000.size() <= 1000) {
					QUtils.batchDelFile(qiniu, bucket, list1000.toArray(array));
				}
			}else {
				flag = false;
			}
		}
	}
}

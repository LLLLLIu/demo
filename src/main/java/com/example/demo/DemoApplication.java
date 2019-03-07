package com.example.demo;

import java.io.IOException;
import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.example.demo.entity.Qiniu;
import com.example.demo.task.AddTask;
import com.example.demo.task.DelTask;

@SpringBootApplication
public class DemoApplication {

	public static Properties props;
	
	static {
		 try {
			props = PropertiesLoaderUtils.loadAllProperties("application.properties");
		} catch (IOException e) {
			System.out.println("加载application.properties文件失败");
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(DemoApplication.class, args);
		Qiniu qiniu = new Qiniu(DemoApplication.props.getProperty("qiniu.ak"), DemoApplication.props.getProperty("qiniu.sk"));
		String bucket = "images";//空间名称
		String prefix = "upload/vod/";
		// 查询文件列表
		
		AddTask addTask = new AddTask(qiniu, bucket, prefix);
		addTask.setName("addTask-000");
		
		DelTask task1 = new DelTask(qiniu, bucket);
		task1.setName("deltask-001");
		
		addTask.start();
		task1.start();
	}

}

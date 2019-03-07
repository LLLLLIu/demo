package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.api.QUtils;
import com.example.demo.entity.Qiniu;
import com.example.demo.task.AddTask;
import com.example.demo.task.DelTask;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

	@Test
	public void contextLoads() throws InterruptedException {
		
	}

}

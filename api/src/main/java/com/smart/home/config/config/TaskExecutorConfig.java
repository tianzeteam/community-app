package com.smart.home.config.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
public class TaskExecutorConfig {

	@Bean 
	public TaskScheduler taskScheduler() { 
		ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler(); 
		//线程池大小 
		scheduler.setPoolSize(15); 
		//线程名字前缀 
		scheduler.setThreadNamePrefix("spring-task-thread"); 
		return scheduler; 
	}

}
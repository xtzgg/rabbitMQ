package com.rabbit.second.quickstart;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {
		public static void main(String[] args) throws IOException, TimeoutException {
				//1 创键连接工厂connectionFactory，并进行配置
				ConnectionFactory connectionFactory = new ConnectionFactory();
				connectionFactory.setHost("47.98.223.30");
				connectionFactory.setPort(5672);
				connectionFactory.setVirtualHost("/");
				connectionFactory.setUsername("guest");
				connectionFactory.setPassword("guest");

				//2 通过连接工厂创建连接
				Connection connection =
						connectionFactory.newConnection();
				//3 通过connection创建一个channel
				Channel channel = connection.createChannel();
				//4 通过channel发送数据
				for (int i=0;i<5;i++){
						String msg = "hello mq----"+i;
						channel.basicPublish("","test001_s",null,msg.getBytes());
				}
				//关闭连接
				channel.close();
				connection.close();
		}
}

package com.rabbit.second.topic;

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
				String exchangName="test_topic_exchange_001";
//				String exchangeType="topic";
				String routingkey1 = "test_topic.firstsufix.secondsufix";
				String routingkey2= "test_topic.firstsufix03";
				String routingkey3 = "test_topic.firstsufix01";
//				String queueName = "test_topic_queue";
				//4 通过channel发送数据
				String msg1 = "hello mq----1";
				String msg2= "hello mq----2";
				String msg3 = "hello mq----3";
				channel.basicPublish(exchangName,routingkey1,null,msg1.getBytes());
				channel.basicPublish(exchangName,routingkey2,null,msg2.getBytes());
				channel.basicPublish(exchangName,routingkey3,null,msg3.getBytes());

				//关闭连接
				channel.close();
				connection.close();
		}
}

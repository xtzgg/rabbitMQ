package com.rabbit.second.direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 初次进行生产消费的发送,使用 default Exchange
 */
public class Consumer {
		public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
				//1 创键连接工厂connectionFactory，并进行配置
				ConnectionFactory connectionFactory = new ConnectionFactory();
				connectionFactory.setHost("47.98.223.30");
				connectionFactory.setPort(5672);
				connectionFactory.setVirtualHost("/");
				connectionFactory.setUsername("guest");
				connectionFactory.setPassword("guest");

				//2 通过连接工厂创建连接
				Connection connection = connectionFactory.newConnection();
				//3 通过connection创建一个channel
				Channel channel = connection.createChannel();

				String routingkey = "test.direct";
				String queueName = "test_direct_queue";
				//4 声明一个队列(创建)
				channel.queueDeclare(queueName,true,false,false,null);

				//5 创建消费者 该消费者建立在哪个连接之上
				QueueingConsumer queueingConsumer = new QueueingConsumer(channel);
				//6 设置channel
				channel.basicConsume(queueName,true,queueingConsumer);
				//7 获取消息
				while (true){
						QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
						String msg = new String(delivery.getBody());
						System.err.println("消费端："+msg);
				}

		}
}

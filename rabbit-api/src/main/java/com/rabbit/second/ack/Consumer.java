package com.rabbit.second.ack;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 初次进行生产消费的发送,使用 default Exchange
 */
public class Consumer {
		public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
				ConnectionFactory connectionFactory = new ConnectionFactory();
				connectionFactory.setHost("47.98.223.30");
				connectionFactory.setPort(5672);
				connectionFactory.setUsername("guest");
				connectionFactory.setPassword("guest");
				connectionFactory.setVirtualHost("/");

				Connection connection = connectionFactory.newConnection();
				Channel channel = connection.createChannel();
				String exchangeName = "test_ack_exchange";
				String routingKey = "ack.#";
				String exchangeType = "topic";
				String queueName = "test_ack_queue";
																		//持久化  自动删除
				channel.exchangeDeclare(exchangeName,exchangeType,false,false,null);
													//持久化   独占    自动删除   map
				channel.queueDeclare(queueName,false,false,false,null);

				channel.queueBind(queueName,exchangeName,routingKey);
				//手工ack，关闭autoACK
				channel.basicConsume(queueName,false,new MyConsumer(channel));


		}
}

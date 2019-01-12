package com.rabbit.second.limitFlow;

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
				String exchangeName = "test_qos_exchange";
				String routingKey = "qos.#";
				String exchangeType = "topic";
				String queueName = "test_qos_queue";
																		//持久化  自动删除
				channel.exchangeDeclare(exchangeName,exchangeType,false,false,null);
													//持久化   独占    自动删除   map
				channel.queueDeclare(queueName,false,false,false,null);

				channel.queueBind(queueName,exchangeName,routingKey);

				// 1 限流(3点)：生产端限制(未实现) 消费端限制条数 是否在channel级别限制
				channel.basicQos(0,1,false);

				// 2 autoACK设置为false;关闭自动签收
				channel.basicConsume(queueName,false,new MyConsumer(channel));
				// 3 在消费者ack应答

		}
}

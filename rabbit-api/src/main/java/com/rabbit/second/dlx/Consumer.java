package com.rabbit.second.dlx;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
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
				String exchangeName = "test_dlx_exchange";
				String routingKey = "dlx.#";
				String exchangeType = "topic";
				String queueName = "test_dlx_queue";

				Map<String ,Object> arguments = new HashMap<>();
				arguments.put("x-dead-letter-exchange","dlx.exchange");
				                                                        //持久化  自动删除
				channel.exchangeDeclare(exchangeName,exchangeType,false,false,null);
													//持久化   独占    自动删除   map
				channel.queueDeclare(queueName,false,false,false,arguments);

				channel.queueBind(queueName,exchangeName,routingKey);

				//死信队列的设置
				channel.exchangeDeclare("dlx.exchange","topic",false,false,null);
				channel.queueDeclare("dlx.queue",false,false,false,null);
				channel.queueBind("dlx.queue","dlx.exchange","#");

				channel.basicQos(0,1,false);
				channel.basicConsume(queueName,false,new MyConsumer(channel));

		}
}

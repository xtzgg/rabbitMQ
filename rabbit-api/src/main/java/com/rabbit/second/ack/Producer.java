package com.rabbit.second.ack;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class Producer {
		public static void main(String[] args) throws IOException, TimeoutException {
				ConnectionFactory connectionFactory = new ConnectionFactory();
				connectionFactory.setHost("47.98.223.30");
				connectionFactory.setPort(5672);
				connectionFactory.setUsername("guest");
				connectionFactory.setPassword("guest");
				connectionFactory.setVirtualHost("/");

				Connection connection = connectionFactory.newConnection();
				Channel channel = connection.createChannel();
				String exchangeName = "test_ack_exchange";
				String routingKey = "ack.save";

				for(int i=0;i<5;i++){
						String msg = "this is a ack BackFunction "+i;
						Map<String,Object> headers = new HashMap<>();
						headers.put("num",1);
						AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
								.deliveryMode(2)
								.contentEncoding("utf-8")
								.headers(headers)
								.build();
						channel.basicPublish(exchangeName,routingKey,true,properties,msg.getBytes());//returnListener收到消息
				}

		}
}

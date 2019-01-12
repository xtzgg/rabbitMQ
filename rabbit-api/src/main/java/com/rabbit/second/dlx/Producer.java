package com.rabbit.second.dlx;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
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
				String exchangeName = "test_dlx_exchange";
				String routingKey = "dlx.save";
				String msg = "this is a self dlx BackFunction 1";
				for(int i=0;i<5;i++){
						AMQP.BasicProperties basicProperties = new AMQP.BasicProperties().builder()
								//.expiration("10000")
								.build();
						channel.basicPublish(exchangeName,routingKey,true,basicProperties,msg.getBytes());//returnListener收到消息
				}

		}
}

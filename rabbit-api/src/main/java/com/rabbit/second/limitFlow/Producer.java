package com.rabbit.second.limitFlow;

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
				String exchangeName = "test_qos_exchange";
				String routingKey = "qos.save";
				String msg = "this is a self consumer BackFunction 1";
				for(int i=0;i<5;i++){
						channel.basicPublish(exchangeName,routingKey,true,null,msg.getBytes());//returnListener收到消息
				}

		}
}

package com.rabbit.second.confirmListener;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
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
				//指定消息确认模式
				channel.confirmSelect();

				String exchangeName = "test_confirm_exchange";
				String routingKey = "confirmListener.save";
				for (int i=0;i<5;i++){
						String msg = "this is a  confirmListener message----"+1;
						channel.basicPublish(exchangeName,routingKey,false,false,null,msg.getBytes());
				}
				//confirm监听
				channel.addConfirmListener(new ConfirmListener() {
						@Override
						public void handleAck(long deliverTag, boolean multiple) throws IOException {
								System.err.println("==========ack");
						}
						@Override
						public void handleNack(long deliverTag, boolean multiple) throws IOException {
								System.err.println("==========no ack");
						}
				});
	/*			channel.close();
				connection.close();*/
		}
}

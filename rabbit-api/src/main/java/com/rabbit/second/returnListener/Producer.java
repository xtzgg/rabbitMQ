package com.rabbit.second.returnListener;

import com.rabbitmq.client.*;

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
				String exchangeName = "test_return_exchange";
				String routingKey = "return.save";
				String routingKeyError = "abc.save";
				String msg = "this is a returnBackFunction 1";

				//mandatory设置为true则发送路由不到的消息会后续处理，false，路由不到的消息会被broker自动删除
				//channel.basicPublish(exchangeName,routingKey,true,null,msg.getBytes());//returnListener收不到到消息
				channel.basicPublish(exchangeName,routingKeyError,true,null,msg.getBytes());//returnListener收到消息
				//channel.basicPublish(exchangeName,routingKeyError,false,null,msg.getBytes());returnListener收不到消息

				channel.addReturnListener(new ReturnListener() {
						@Override
						public void handleReturn(int replyCode, String replyText, String exchange, String routingKey,
								AMQP.BasicProperties basicProperties, byte[] body) throws IOException {
								System.err.println("------------handle return ---------");
								System.err.println("replyCode: "+replyCode);
								System.err.println("replyText: "+replyText);
								System.err.println("exchange: "+exchange);
								System.err.println("basicProperties: "+basicProperties.getDeliveryMode());
								System.err.println("body: "+new String(body));

						}
				});
		}
}

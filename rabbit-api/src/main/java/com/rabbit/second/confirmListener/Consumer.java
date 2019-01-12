package com.rabbit.second.confirmListener;

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
				ConnectionFactory connectionFactory = new ConnectionFactory();
				connectionFactory.setHost("47.98.223.30");
				connectionFactory.setPort(5672);
				connectionFactory.setUsername("guest");
				connectionFactory.setPassword("guest");
				connectionFactory.setVirtualHost("/");
				Connection connection = connectionFactory.newConnection();
				Channel channel = connection.createChannel();

				String exchangeName = "test_confirm_exchange";
				String routingKey = "confirmListener.#";
				String exchangeType = "topic";
				String queueName = "test_confirm_queue";
				//声明交换机和队列，绑定队列，指定路由key 			自动删除
				channel.exchangeDeclare(exchangeName,exchangeType,true,false,null);
				channel.queueDeclare(queueName,true,false,false,null);
				channel.queueBind(queueName,exchangeName,routingKey);

				QueueingConsumer queueingConsumer = new QueueingConsumer(channel);
												   //自动签收
				channel.basicConsume(queueName, true, queueingConsumer);
				while (true){
						QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
						System.err.println(new String(delivery.getBody()));
				}

		}
}

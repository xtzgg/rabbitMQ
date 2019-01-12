package com.rabbit.second.message;

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
				//1 创键连接工厂connectionFactory，并进行配置
				ConnectionFactory connectionFactory = new ConnectionFactory();
				connectionFactory.setHost("47.98.223.30");
				connectionFactory.setPort(5672);
				connectionFactory.setVirtualHost("/");
				connectionFactory.setUsername("guest");
				connectionFactory.setPassword("guest");

				//2 通过连接工厂创建连接
				Connection connection =
						connectionFactory.newConnection();
				//3 通过connection创建一个channel
				Channel channel = connection.createChannel();
				String exchangName="test_direct_exchange";
				String exchangeType="direct";
				String routingkey = "test.direct";
				String queueName = "test_direct_queue";
				Map<String,Object> headers = new HashMap<>();
				headers.put("我是","小猫");
				headers.put("你是","小狗");
				AMQP.BasicProperties basicProperties = new AMQP.BasicProperties.Builder()
						.deliveryMode(2)//1 服务器重启后，消息未被消费则消失，2 消息服务重启不会丢失
						.contentEncoding("utf-8")
						.expiration("10000")//过期时间
						.headers(headers)//自定义属性
						.build();
				//4 通过channel发送数据
				for (int i=0;i<5;i++){
						String msg = "hello mq----"+i;
						channel.basicPublish(exchangName,routingkey,basicProperties,msg.getBytes());
				}
				//关闭连接
				channel.close();
				connection.close();
		}
}

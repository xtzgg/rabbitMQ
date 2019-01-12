package com.rabbit.second.ack;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

public class MyConsumer extends DefaultConsumer {
		private Channel channel;
		public MyConsumer(Channel channel) {
				super(channel);
				this.channel = channel;
		}

		@Override							//标签
		public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
				throws IOException {
				System.err.println("consumerTag: "+ consumerTag);
				System.err.println("envelope: "+ envelope.getExchange() + "--" + envelope.getDeliveryTag());
				System.err.println("properties: "+ properties);
				System.err.println("body: "+ new String(body));
				if(properties.getHeaders().get("num").equals(1)){ //如果我获得这个标记，则执行noack
						//long deliveryTag, boolean multiple, boolean requeue 为true则重回队列
						channel.basicNack(envelope.getDeliveryTag(),false,true);
				}else{
						channel.basicAck(envelope.getDeliveryTag(),false);//否则则继续消费下一条消息
				}

		}
}

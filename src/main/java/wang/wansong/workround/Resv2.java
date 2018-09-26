package wang.wansong.workround;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;

import wang.wansong.utils.ConnectionUtils;

public class Resv2 {

	private static final String QUEUE_NAME = "work_queue";
	
	public static void main(String[] args) throws IOException, TimeoutException {
		// 获取连接
		Connection connection = ConnectionUtils.getConnection();
		
		// 创建通道
		Channel channel = connection.createChannel();
		
		// 声明channel关联的队列
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		
		// 创建消费者
		DefaultConsumer consumer = new DefaultConsumer(channel){
			
			// 处理到达的消息
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
					throws IOException {
				
				System.out.println("resv2: " + new String(body, "utf-8"));
				
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		
		channel.basicConsume(QUEUE_NAME, true, consumer);
	}
}

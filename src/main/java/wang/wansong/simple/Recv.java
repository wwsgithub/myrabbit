package wang.wansong.simple;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;

import wang.wansong.utils.ConnectionUtils;

/**
 * 
 * @Description:接收
 * @author:wws
 * @time:2018年7月24日 下午3:30:08
 */
public class Recv {
	
	private static final String QUEUE_NAME = "simple_queue";
	
	public static void main(String[] args) throws IOException, TimeoutException {
		
		// 获取连接
		Connection connection = ConnectionUtils.getConnection();
		
		// 创建channel
		Channel channel = connection.createChannel();
		
		// 队列声明（如有队列，可不声明）
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		
		// 定义消费者
		DefaultConsumer consumer = new DefaultConsumer(channel){
			/**
			 * 处理
			 */
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
					throws IOException {
				
				System.out.println(new String(body));
				
			}
		};
		
		// 监听队列
		channel.basicConsume(QUEUE_NAME, true, consumer);
		
	}
}

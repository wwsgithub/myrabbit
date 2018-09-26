package wang.wansong.routing;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;

import wang.wansong.utils.ConnectionUtils;

public class Resv2 {

	private static final String QUEUE_NAME = "test_queue_direct_2";
	
	private static final String EXCHANGE_NAME = "test_exchange_direct";
	
	public static void main(String[] args) throws IOException, TimeoutException {
		// 获取连接
		Connection connection = ConnectionUtils.getConnection();
		
		// 创建channel
		final Channel channel = connection.createChannel();
		
		// 声明队列
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		
		// 绑定交换器，添加路由键
		channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "demo");
		channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "prod");
		
		// 每次分发一条消息
		channel.basicQos(1);
		
		Consumer consumer = new DefaultConsumer(channel){
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
					throws IOException {
				String msg = new String(body, "utf-8");
				
				System.out.println("[2]: " + msg);
				
				// 手动回执
				channel.basicAck(envelope.getDeliveryTag(), false);
			}
		};
		
		// 消费（关闭自动应答）
		channel.basicConsume(QUEUE_NAME, false, consumer);
	}
}

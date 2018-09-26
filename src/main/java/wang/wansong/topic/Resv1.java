package wang.wansong.topic;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;

import wang.wansong.utils.ConnectionUtils;

/**
 * topic消费者
 * 路由键通配
 * #: 一个或多个
 * *: 一个
 * @Description:
 * @author:wws
 * @time:2018年8月7日 下午2:36:11
 */
public class Resv1 {

	private static final String EXCHANGE_NAME = "test_exchange_topic";
	private static final String QUEUE_NAME = "test_queue_topic_1";

	public static void main(String[] args) throws IOException, TimeoutException {
		// 获取连接
		Connection connection = ConnectionUtils.getConnection();
		
		// 创建channel
		final Channel channel = connection.createChannel();
		
		// 声明队列
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		
		// 队列绑定交换器
		channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "goods.add");
		
		// 每次分发一条
		channel.basicQos(1);
		
		// 定义消费者
		Consumer consumer = new DefaultConsumer(channel){
			/**
			 * 处理消息到达
			 */
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
					throws IOException {
				System.out.println(new String(body, "utf-8"));
				// 手动回执
				channel.basicAck(envelope.getDeliveryTag(), false);
			}
		};
		
		// 关闭自动应答
		channel.basicConsume(QUEUE_NAME, false, consumer);
	}
}

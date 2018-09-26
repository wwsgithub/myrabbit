package wang.wansong.ps;

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
 * 订阅消费者
 * @Description:TODO
 * @author:wws
 * @time:2018年7月30日 下午5:34:49
 */
public class Resv1 {

	private static final String QUEUE_NAME = "test_queue_fanout_email";
	private static final String EXCHANGE_NAME = "test_exchange_fanout";
	
	public static void main(String[] args) throws IOException, TimeoutException {
		// 获取连接
		Connection connection = ConnectionUtils.getConnection();
		
		// 创建channel
		final Channel channel = connection.createChannel();
		
		// 声明队列
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		
		// 绑定队列到转换器
		channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "");
		
		// 只分发一条
		channel.basicQos(1);
		
		Consumer consumer = new DefaultConsumer(channel){
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
					throws IOException {
				String msg = new String(body, "utf-8");
				
				System.out.println("[1]: " + msg);
				
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally{
					// 手动回执
					channel.basicAck(envelope.getDeliveryTag(), false);
				}
			}
		};
		
		channel.basicConsume(QUEUE_NAME, false, consumer);
	}
}

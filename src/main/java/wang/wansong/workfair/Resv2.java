package wang.wansong.workfair;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;

import wang.wansong.utils.ConnectionUtils;

public class Resv2 {

	private static final String QUEUE_NAME = "work_queue";
	
	public static void main(String[] args) throws IOException, TimeoutException {
		// 获取连接
		Connection connection = ConnectionUtils.getConnection();
		
		// 创建通道
		final Channel channel = connection.createChannel();
		
		// 声明channel关联的队列
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		
		// 每次分发一条
		channel.basicQos(1);
		
		// 创建消费者
		Consumer consumer = new DefaultConsumer(channel){
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
					throws IOException {
				String msg = new String(body, "utf-8");
				
				System.out.println("[2]: " + msg);
				
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally{
					System.out.println("[2] done");
					
					// 手动回执
					channel.basicAck(envelope.getDeliveryTag(), false);
				}
			}
		};
		
		// 关闭自动应答
		boolean autoAck = false;
		channel.basicConsume(QUEUE_NAME, autoAck , consumer);
	}
}

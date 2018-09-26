package wang.wansong.workfair;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import wang.wansong.utils.ConnectionUtils;

/**
 * 公平分发（fair dispatch） 能者多劳
 * 	1. 设置Qos（分发数量）
 * 	2. consumer处理完消息，手动回执
 * 	3. 关闭自动问答ack
 * @Description:TODO
 * @author:wws
 * @time:2018年7月30日 下午2:56:04
 */
public class Send {

	private static final String QUEUE_NAME = "work_queue";
	
	/*				
	 * 工作队列 
	 * 				 |--c1
	 * p-----Queue---|
	 * 				 |--c2
	 */
	public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
		// 获取连接
		Connection connection = ConnectionUtils.getConnection();
		
		// 创建channel
		Channel channel = connection.createChannel();
		
		// 声明队列
		channel.queueDeclare(QUEUE_NAME, true, false, false, null);
		
		/*
		 * 消费者发送确认标志后，才会发送新消息
		 * 每次发送prefetchCount条
		 */
		int prefetchCount = 1;
		channel.basicQos(prefetchCount);
		
		for (int i = 0; i < 50; i++) {
			String msg = "hello, " + i;
			
			System.out.println(msg);
			
			channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
			
			Thread.sleep(i * 4);
		}
		
		channel.close();
		connection.close();
	}
}

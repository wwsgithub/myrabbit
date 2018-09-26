package wang.wansong.workround;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import wang.wansong.utils.ConnectionUtils;

/**
 * 轮询分发（round dispatch）
 * @Description:TODO
 * @author:wws
 * @time:2018年7月30日 下午2:56:52
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
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		
		for (int i = 0; i < 50; i++) {
			String msg = "hello " + i;
			
			System.out.println(msg);
			
			channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
			
			Thread.sleep(i * 30);
		}
		
		channel.close();
		connection.close();
	}
}

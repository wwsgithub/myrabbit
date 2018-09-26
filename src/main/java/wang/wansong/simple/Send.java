package wang.wansong.simple;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import wang.wansong.utils.ConnectionUtils;

/**
 * 
 * @Description:简单队列
 * @author:wws
 * @time:2018年7月24日 下午3:29:56
 */
public class Send {

	private static final String QUEUE_NAME = "simple_queue";
	
	public static void main(String[] args) throws IOException, TimeoutException {
		// 获取连接
		Connection connection = ConnectionUtils.getConnection();
		
		// 从连接中获取一个通道
		Channel channel = connection.createChannel();
		
		// 创建队列声明
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		
		String msg = "hello queue！1";
		
		channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
		
		channel.close();
		
		connection.close();
	}
}

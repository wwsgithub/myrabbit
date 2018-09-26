package wang.wansong.topic;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import wang.wansong.utils.ConnectionUtils;

/**
 * topic主题模式
 * @Description:
 * @author:wws
 * @time:2018年8月7日 下午2:20:00
 */
public class Send {

	private static final String EXCHANGE_NAME = "test_exchange_topic";

	public static void main(String[] args) throws IOException, TimeoutException {
		// 获取连接
		Connection connection = ConnectionUtils.getConnection();
		
		// 创建channel
		Channel channel = connection.createChannel();
		
		// 声明交换器（转发器）-处理路由键
		channel.exchangeDeclare(EXCHANGE_NAME, "topic");
		
		channel.basicPublish(EXCHANGE_NAME, "goods.add", null, "goods".getBytes());
		
		System.out.println("---send---");
		
		channel.close();
		connection.close();
	}
}

package wang.wansong.routing;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import wang.wansong.utils.ConnectionUtils;

/**
 * 路由模式 - direct
 * 
 * @Description:TODO
 * @author:wws
 * @time:2018年8月3日 下午3:18:14
 */
public class Send {

	private static final String EXCHANGE_NAME = "test_exchange_direct";
	
	public static void main(String[] args) throws IOException, TimeoutException {
		// 获取连接
		Connection connection = ConnectionUtils.getConnection();
		
		// 创建channel
		Channel channel = connection.createChannel();
		
		// 声明交换器（转发器）
		channel.exchangeDeclare(EXCHANGE_NAME, "direct");
		
		String routingKey = "prod";
		String msg = "hello routing!";
		channel.basicPublish(EXCHANGE_NAME, routingKey , null, msg.getBytes());
		
		System.out.println(msg);
		
		channel.close();
		connection.close();
	}
}

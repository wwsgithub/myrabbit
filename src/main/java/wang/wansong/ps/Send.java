package wang.wansong.ps;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import wang.wansong.utils.ConnectionUtils;

/**
 * 发布订阅
 * @Description:TODO
 * @author:wws
 * @time:2018年7月30日 下午5:21:39
 */
public class Send {

	private static final String EXCHANGE_NAME = "test_exchange_fanout";
	
	public static void main(String[] args) throws IOException, TimeoutException {
		
		// 获取连接
		Connection connection = ConnectionUtils.getConnection();
		
		// 创建channel
		Channel channel = connection.createChannel();
		
		// 声明转换器
		channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
		
		// 发送消息
		String msg = "hello exchange";
		channel.basicPublish(EXCHANGE_NAME, "", null, msg.getBytes());
		
		// 关闭资源
		channel.close();
		connection.close();
	}
}

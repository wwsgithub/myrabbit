package wang.wansong.utils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ConnectionUtils {

	/**
	 * 
	 * @Description:Connection
	 * @return
	 * Connection
	 * @exception:
	 * @author: wws
	 * @throws TimeoutException 
	 * @throws IOException 
	 * @time:2018年7月24日 上午10:27:45
	 */
	public static Connection getConnection() throws IOException, TimeoutException{
		// 定义一个连接工厂
		ConnectionFactory factory = new ConnectionFactory();
		
		// 设置服务地址
		factory.setHost("127.0.0.1");
		
		// 协议：AMQP, 端口：5672
		factory.setPort(5672);
		
		// 设置virtul host
		factory.setVirtualHost("/virtual_wws");
		
		// 用户名
		factory.setUsername("user_wws");
		
		// 密码
		factory.setPassword("123456");
		
		return factory.newConnection();
	}
}

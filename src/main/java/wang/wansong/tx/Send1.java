package wang.wansong.tx;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import wang.wansong.utils.ConnectionUtils;

/**
 * AMQP事务模式
 * txSelect
 * txCommit
 * txRollback
 * @Description:
 * @author:wws
 * @time:2018年8月9日 下午4:32:34
 */
public class Send1 {

	private static final String QUEUE_NAME = "test_queue_tx";

	public static void main(String[] args) throws IOException, TimeoutException {
		// 获取连接
		Connection connection = ConnectionUtils.getConnection();
		
		// 创建channel
		Channel channel = connection.createChannel();
		
		// 开启事务
		channel.txSelect();
		
		try {
			channel.basicPublish("", QUEUE_NAME, null, "hello tx!".getBytes());
			
			int i = 1/0;
			
			channel.txCommit();

			System.out.println("send done.");
		} catch (Exception e) {
			channel.txRollback();
		}
		
		channel.close();
		connection.close();
	}
}

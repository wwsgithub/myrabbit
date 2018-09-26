package wang.wansong.confirm;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import wang.wansong.utils.ConnectionUtils;

/**
 * confirm模式：
 * 普通模式：每次发一条
 * 批量模式：每次发多条
 * 异步模式：异步发送
 * @Description:
 * @author:wws
 * @time:2018年8月9日 下午5:55:44
 */
public class Send1 {

	private static final String QUEUE_NAME = "test_queue_confirm1";

	public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
		// 获取连接
		Connection connection = 
				ConnectionUtils.getConnection();
		
		// 创建channel
		Channel channel = connection.createChannel();
		
		// 声明队列
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		
		// 开启confirm模式
		channel.confirmSelect();
		
//		channel.basicPublish("", QUEUE_NAME, null, "hello confirm!".getBytes());
		
		for(int i = 0;i < 10; i++){
			channel.basicPublish("", QUEUE_NAME, null, "hello confirm!".getBytes());
		}
		
		if(!channel.waitForConfirms()){
			System.out.println("send fail.");
		}else{
			System.out.println("send succ.");
		}
		
		channel.close();
		connection.close();
	}
}

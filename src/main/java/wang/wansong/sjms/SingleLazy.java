package wang.wansong.sjms;

import java.util.Optional;

/**
 * 懒汉
 * @Description:
 * @author:wws
 * @time:2018年8月16日 上午11:18:42
 */
public class SingleLazy {

	// 默认为null，需要使用时，再创建实例
	private static SingleLazy singleLazy = null;
	
	private SingleLazy(){}
	
	/**
	 * @Description:使用时判断singleLazy是否为空，不为空返回对象，为空新建；
	 * 				但是可能会有并发问题，需要加上synchronized
	 * @return
	 * SingleLazy
	 * @exception:
	 * @author: wws
	 * @time:2018年8月16日 上午11:21:17
	 */
	public static synchronized SingleLazy getInstance(){
		return Optional.ofNullable(singleLazy).orElse(new SingleLazy());
	}
}

package wang.wansong.sjms;

/**
 * 饿汉
 * @Description:
 * @author:wws
 * @time:2018年8月16日 上午11:14:58
 */
public class SingleHungry {

	// 默认创建一个实例
	private static SingleHungry singleHungry = new SingleHungry();
	
	// 使用private禁止创建新实例
	private SingleHungry(){}
	
	// 获取实例的方法
	public static SingleHungry getInstance(){
		return singleHungry;
	}
}

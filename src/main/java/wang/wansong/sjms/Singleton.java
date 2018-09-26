package wang.wansong.sjms;

/**
 * 单例类
 * @Description:
 * @author:wws
 * @time:2018年8月15日 下午8:11:22
 */
public class Singleton {

	private static final Singleton singleton = new Singleton();
	
	// 使用private的构造函数确保应用中无法再产生实例
	private Singleton(){}
	
	// 获取实例的方法
	public static Singleton getSingleton(){
		return singleton;
	}
	
	// 其他方法尽量为static
	public static void doSomething(){
		
	}
}

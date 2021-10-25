package util;


/**
 * <p><b>类名:</b>{@code Timer}
 * <p><b>功能:</b><br>用来计时
 * <p><b>方法：</b>
 * <br>{@link #start()}开始计时
 * <br>{@link #end()}停止计时
 */
public class Timer {
	private double startTime,endTime,time;
	/**
	* <p><b>方法名:</b>{@code start()}
	* <p><b>功能:</b><br>开始计时
	* <p><b>其它同名方法:</b>
	* <br>{@link #start(int, String)}
	*/
	public void start() {
		startTime=System.currentTimeMillis();
	}
	public void start(int mode,String str) {
		if(mode==1) {
			System.out.print(str);
		}else {
			System.out.println(str);
		}
		startTime=System.currentTimeMillis();
	}
	public double end() {
		endTime=System.currentTimeMillis();
		return (endTime-startTime)/1000;
	}
	public void end(int mode,String str) {
		time=end();
		if(mode==1) {
			System.out.println(str+time);
		}else {
			System.out.println(time+str);
		}
	}
	public void end(String str1,String str2) {
		time=end();
		System.out.println(str1+time+str2);
	}
	public static void main(String[] args) {

	}

}

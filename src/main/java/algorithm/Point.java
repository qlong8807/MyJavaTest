package algorithm;
public class Point {
	/*** 点的X坐标 */
	private double x = 0;
	/*** 点的Y坐标 */
	private double y = 0;
	/*** 点所属的曲线的索引 */
	private int index = 0;
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	/***
	 * 点数据的构造方法**
	 * 
	 * @paramx 点的X坐标*@paramy*点的Y坐标*@paramindex点所属的曲线的索引
	 */
	public Point(double x, double y, int index)
	{
		this.x = x;
		this.y = y;
		this.index = index;
	}
}

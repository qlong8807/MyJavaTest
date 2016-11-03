package algorithm;
/**
 * 点到线的最短距离
 * @author Administrator
 */
public class PointToLineShortDistance {
	public static void main(String[] args) {
		double pointToLine = PointToLineShortDistance.pointToLine(0, 0, 5, 5, 2, 3);
		System.err.println(pointToLine);
	}
	/**
	 * 点到直线的最短距离的判断 点（x0,y0） 到由两点组成的线段(x1,y1) ,( x2,y2 )
	 * @return
	 */
	public static double pointToLine(int x1, int y1, int x2, int y2, int x0,int y0) {
		double space = 0;
		double a, b, c;
		a = lineSpace(x1, y1, x2, y2);// 线段的长度
		b = lineSpace(x1, y1, x0, y0);// (x1,y1)到点的距离
		c = lineSpace(x2, y2, x0, y0);// (x2,y2)到点的距离
		if (c <= 0.000001 || b <= 0.000001) {
			space = 0;
			return space;
		}
		if (a <= 0.000001) {
			space = b;
			return space;
		}
		if (c * c >= a * a + b * b) {
			space = b;
			return space;
		}
		if (b * b >= a * a + c * c) {
			space = c;
			return space;
		}
		double p = (a + b + c) / 2;// 半周长
		double s = Math.sqrt(p * (p - a) * (p - b) * (p - c));// 海伦公式求面积
		space = 2 * s / a;// 返回点到线的距离（利用三角形面积公式求高）
		return space;
	}
	// 计算两点之间的距离
	private static double lineSpace(int x1, int y1, int x2, int y2) {
		double lineLength = 0;
		lineLength = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2)
		* (y1 - y2));
		return lineLength;
	}
}

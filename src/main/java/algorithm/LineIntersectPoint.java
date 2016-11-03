package algorithm;

import java.awt.geom.Point2D;

public class LineIntersectPoint {
	public static void main(String[] args) {
		//方法一
		/*long a = System.nanoTime();
		for (int i = 0; i < 100; i++) {
			Point2D p1 = new Point2D.Double(10, 20);
			Point2D p2 = new Point2D.Double(100, 200);

			Point2D p3 = new Point2D.Double(50, 20);
			Point2D p4 = new Point2D.Double(20, 100);

			Param pm1 = CalParam(p1, p2);
			Param pm2 = CalParam(p3, p4);
			Point2D rp = getIntersectPoint(pm1, pm2);
			System.out.println("他们的交点为: (" + rp.getX() + "," + rp.getY() + ")");
		}
		long b = System.nanoTime();
		System.err.println("=="+(b-a));*/
		//方法二
		long a2 = System.nanoTime();
		for (int i = 0; i < 100; i++) {
			get();
		}
		long b2 = System.nanoTime();
		System.err.println("--"+(b2-a2));
	}

	public static void get(){
		double x1 = 10, y1 = 20, x2 = 100, y2 = 200;   
//		double a = (y1 - y2) / (x1 - x2);  
//		double b = (x1 * y2 - x2 * y1) / (x1 - x2);  
//		System.out.println("求出该直线方程为: y=" + a + "x + " + b);  
		  
		//第二条  
		double x3 = 50, y3 = 20, x4 = 20, y4 = 100;  
//		double c = (y3 - y4) / (x3 - x4);  
//		double d = (x3 * y4 - x4 * y3) / (x3 - x4);  
//		System.out.println("求出该直线方程为: y=" + c + "x + " + d);  
		  
		double x = ((x1 - x2) * (x3 * y4 - x4 * y3) - (x3 - x4) * (x1 * y2 - x2 * y1))  
		    / ((x3 - x4) * (y1 - y2) - (x1 - x2) * (y3 - y4));  
		  
		double y = ((y1 - y2) * (x3 * y4 - x4 * y3) - (x1 * y2 - x2 * y1) * (y3 - y4))  
		    / ((y1 - y2) * (x3 - x4) - (x1 - x2) * (y3 - y4));  
		  
		System.out.println("他们的交点为: (" + x + "," + y + ")");  
	}
	/**
	 * 计算两点的直线方程的参数a,b,c
	 * 
	 * @param p1
	 * @param p2
	 * @return
	 */
	public static Param CalParam(Point2D p1, Point2D p2) {
		double a, b, c;
		double x1 = p1.getX(), y1 = p1.getY(), x2 = p2.getX(), y2 = p2.getY();
		a = y2 - y1;
		b = x1 - x2;
		c = (x2 - x1) * y1 - (y2 - y1) * x1;
		if (b < 0) {
			a *= -1;
			b *= -1;
			c *= -1;
		} else if (b == 0 && a < 0) {
			a *= -1;
			c *= -1;
		}
		return new Param(a, b, c);
	}

	/**
	 * 计算两条直线的交点
	 * 
	 * @param pm1
	 * @param pm2
	 * @return
	 */
	public static Point2D getIntersectPoint(Param pm1, Param pm2) {
		return getIntersectPoint(pm1.a, pm1.b, pm1.c, pm2.a, pm2.b, pm2.c);
	}

	public static Point2D getIntersectPoint(double a1, double b1, double c1,
			double a2, double b2, double c2) {
		Point2D p = null;
		double m = a1 * b2 - a2 * b1;
		if (m == 0) {
			return null;
		}
		double x = (c2 * b1 - c1 * b2) / m;
		double y = (c1 * a2 - c2 * a1) / m;
		p = new Point2D.Double(x, y);
		return p;
	}
}

class Param {
	double a;
	double b;
	double c;

	public Param(double a, double b, double c) {
		this.setA(a);
		this.setB(b);
		this.setC(c);
	}

	public double getA() {
		return a;
	}

	public void setA(double a) {
		this.a = a;
	}

	public double getB() {
		return b;
	}

	public void setB(double b) {
		this.b = b;
	}

	public double getC() {
		return c;
	}

	public void setC(double c) {
		this.c = c;
	}

}
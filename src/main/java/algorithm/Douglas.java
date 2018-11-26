package algorithm;

import java.util.ArrayList;
import java.util.List;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

/*** 道格拉斯-普克算法 (亦称为拉默-道格拉斯-普克算法、迭代适应点算法、分裂与合并算法)是将曲线近似表示为一系列点，并减少点的数量的一种算法。
 * 算法的基本思路是:对每一条曲线的首末点虚连一条直线,求所有点与直线的距离,并找出最大距离值dmax ,用dmax与限差D相比:
 * 若dmax <D,这条曲线上的中间点全部舍去;若dmax ≥D,保留dmax 对应的坐标点,并以该点为界,把曲线分为两部分,对这两部分重复使用该方法。
 *  */
public class Douglas {
	/*** 存储采样点数据的链表 */
	public List<Point> points = new ArrayList<Point>();
	/*** 控制数据压缩精度的极差 */
	private static final double D = 5;
	private WKTReader reader;

	/*** 构造Geometry**@paramstr*@return */
	public Geometry buildGeo(String str) {
		try {
			if (reader == null) {
				reader = new WKTReader();
			}
			return reader.read(str);
		} catch (ParseException e) {
			throw new RuntimeException("buildGeometryError", e);
		}
	}

	/*** 读取采样点 */
	public void readPoint() {
//		Geometry g = buildGeo("LINESTRING(14 23,42 66,77 86,95,1010)");
		Geometry g = buildGeo("LINESTRING(0 0,5 10,15 16,20 18,25 40,27 24,30 32,35 36)");
		Coordinate[] coords = g.getCoordinates();
		for (int i = 0; i < coords.length; i++) {
			Point p = new Point(coords[i].x, coords[i].y, i);
			points.add(p);
		}
	}

	/*** 对矢量曲线进行压缩**@paramfrom*曲线的起始点*@paramto*曲线的终止点 */
	public void compress(Point from, Point to) {
		/*** 压缩算法的开关量 */
		boolean switchvalue = false;
		/*** 由起始点和终止点构成的直线方程一般式的系数 */
//		System.out.println(from.getY());
//		System.out.println(to.getY());
		double A = (from.getY() - to.getY())
				/ Math.sqrt(Math.pow((from.getY() - to.getY()), 2)
						+ Math.pow((from.getX() - to.getX()), 2));
		/*** 由起始点和终止点构成的直线方程一般式的系数 */
		double B = (to.getX() - from.getX())
				/ Math.sqrt(Math.pow((from.getY() - to.getY()), 2)
						+ Math.pow((from.getX() - to.getX()), 2));
		/*** 由起始点和终止点构成的直线方程一般式的系数 */
		double C = (from.getX() * to.getY() - to.getX() * from.getY())
				/ Math.sqrt(Math.pow((from.getY() - to.getY()), 2)
						+ Math.pow((from.getX() - to.getX()), 2));
		double d = 0;
		double dmax = 0;
		int m = points.indexOf(from);
		int n = points.indexOf(to);
		if (n == m + 1)
			return;
		Point middle = null;
		List<Double> distance = new ArrayList<Double>();
		for (int i = m + 1; i < n; i++) {
			d = Math.abs(A * (points.get(i).getX()) + B
					* (points.get(i).getY()) + C)
					/ Math.sqrt(Math.pow(A, 2) + Math.pow(B, 2));
			distance.add(d);
		}
		dmax = distance.get(0);
		for (int j = 1; j < distance.size(); j++) {
			if (distance.get(j) > dmax)
				dmax = distance.get(j);
		}
		System.out.println(dmax);
		if (dmax > D)
			switchvalue = true;
		else
			switchvalue = false;
		if (!switchvalue) {
			// 删除Points(m,n)内的坐标
			for (int i = m + 1; i < n; i++)
				points.get(i).setIndex(-1);
		} else {
			for (int i = m + 1; i < n; i++) {
				if ((Math.abs(A * (points.get(i).getX()) + B
						* (points.get(i).getY()) + C)
						/ Math.sqrt(Math.pow(A, 2) + Math.pow(B, 2)) == dmax))
					middle = points.get(i);
			}
			compress(from, middle);
			compress(middle, to);
		}
	}

	public static void main(String[] args) {
		Douglas d = new Douglas();
		d.readPoint();
		d.compress(d.points.get(0), d.points.get(d.points.size() - 1));
		for (int i = 0; i < d.points.size(); i++) {
			Point p = d.points.get(i);
			if (p.getIndex() > -1) {
				System.out.println(p.getX() + "--->" + p.getY());
			}
		}
		System.err.println(Math.sqrt(Math.pow(-8, 2)));
		System.err.println(Math.abs(-8.77));
	}
}
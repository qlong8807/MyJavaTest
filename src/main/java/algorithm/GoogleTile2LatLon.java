package algorithm;

public class GoogleTile2LatLon {
	public static void main(String[] args) {
		int zoom = 10;
		double lat = 47.968056d;
		double lon = 7.909167d;
		System.out.println("http://tile.openstreetmap.org/"+ getTileNumber(lat, lon, zoom) + ".png");
	}

	public static String getTileNumber(final double lat, final double lon,
			final int zoom) {
		int xtile = (int) Math.floor((lon + 180) / 360 * (1 << zoom));
		int ytile = (int) Math
				.floor((1 - Math.log(Math.tan(Math.toRadians(lat)) + 1
						/ Math.cos(Math.toRadians(lat)))
						/ Math.PI)
						/ 2 * (1 << zoom));
		if (xtile < 0)
			xtile = 0;
		if (xtile >= (1 << zoom))
			xtile = ((1 << zoom) - 1);
		if (ytile < 0)
			ytile = 0;
		if (ytile >= (1 << zoom))
			ytile = ((1 << zoom) - 1);
		return ("" + zoom + "/" + xtile + "/" + ytile);
	}
	public static long xyzToTileId(int z, int x, int y){
		long tileId = 0;
		tileId = (long)(z*Math.pow(10, 10))+(long)(x*Math.pow(10, 5))+y;
		return tileId;
	}
	
	public static int[] tileIdTozxy(long tileId){
		int[] zxy = new int[3];
		zxy[0] = (int)(tileId / Math.pow(10, 10));
		zxy[1] = (int)(tileId / Math.pow(10, 5))%((int)Math.pow(10, 5));
		zxy[2] = (int)(tileId % Math.pow(10, 5));
		return zxy;
	}
	
	public static long getTileNumber(long latitude, long longitude, int zoom) {
		double lat = ((double)latitude)/Math.pow(10, 6);
		double lon = ((double)longitude)/Math.pow(10, 6);
		int[] zxy = new int[3];
		zxy[0] = zoom;
		zxy[1] = (int) Math.floor((lon + 180) / 360 * (1 << zoom));
		zxy[2] = (int) Math.floor((1 - Math.log(Math.tan(Math.toRadians(lat))
				+ 1 / Math.cos(Math.toRadians(lat)))
				/ Math.PI)
				/ 2 * (1 << zoom));
		if (zxy[1] < 0)
			zxy[1] = 0;
		if (zxy[1] >= (1 << zoom))
			zxy[1] = ((1 << zoom) - 1);
		if (zxy[2] < 0)
			zxy[2] = 0;
		if (zxy[2] >= (1 << zoom))
			zxy[2] = ((1 << zoom) - 1);
		return xyzToTileId(zxy[0], zxy[1], zxy[2]);
	}
	BoundingBox tile2boundingBox(final int x, final int y, final int zoom) {
		BoundingBox bb = new BoundingBox();
		bb.north = tile2lat(y, zoom);
		bb.south = tile2lat(y + 1, zoom);
		bb.west = tile2lon(x, zoom);
		bb.east = tile2lon(x + 1, zoom);
		return bb;
	}

	static double tile2lon(int x, int z) {
		return x / Math.pow(2.0, z) * 360.0 - 180;
	}

	static double tile2lat(int y, int z) {
		double n = Math.PI - (2.0 * Math.PI * y) / Math.pow(2.0, z);
		return Math.toDegrees(Math.atan(Math.sinh(n)));
	}

	/**
	 * 经纬度类
	 * @author zyl
	 * @date 2016年1月29日
	 */
	class BoundingBox {
		double north;
		double south;
		double east;
		double west;
	}
	
}

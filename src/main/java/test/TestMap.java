package test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TestMap {
	public static void main(String[] args) {
		Map<Integer,Integer> hmap = new HashMap<Integer, Integer>();
		Map<Integer,Integer> map = new ConcurrentHashMap<Integer, Integer>();
		map.put(7, 7);
		map.put(1, 1);
		map.put(4, 4);
		map.put(3, 3);
		Integer integer = map.get(2);
		if(null != integer)
		System.out.println(integer);
		System.out.println(00);
		hmap.put(1, null);
		hmap.put(2, null);
		hmap.put(3, null);
	}
}

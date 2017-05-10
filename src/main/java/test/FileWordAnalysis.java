/**
 * 
 */
package test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author zyl
 * @date 2017年4月6日
 * 读取文件中的内容并转成一个string，以tab或者空格分割，统计各个单词出现的次数，并输出到另一个文件。
 */
public class FileWordAnalysis {
	public static void main(String[] args) {
		String readFilePath = "C:\\Users\\Administrator\\Desktop\\dbs.txt";
		String readFile = readFile(readFilePath);
		String replace1 = readFile.replace("	", "---");
		String replace2 = replace1.replace(" ", "---");
		String[] split = replace2.split("---");
		List<String> list = Arrays.asList(split);
		Map<String, Integer> map = new HashMap<String, Integer>();
		for(String s : list){
			if(s.startsWith("NUMBER") || s.startsWith("VAR")) continue;
			Integer counts = map.get(s);
			if(null == counts || counts == 0){
				map.put(s, 1);
			}else{
				map.put(s, counts+1);
			}
		}
//		Map<String, Integer> sortMap = sortByValue(map);
		String sortAndGetString = sortAndGetString(map);
		writeToFile(sortAndGetString);
	}
	
	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        Map<K, V> result = new LinkedHashMap<K, V>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }
	
	private static String readFile(String readFilePath){
		try {
			StringBuilder result = new StringBuilder();
			File file = new File(readFilePath);
			if(file.isFile() && file.exists()){ //判断文件是否存在
				InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file));
				BufferedReader reader = new BufferedReader(inputStreamReader);
				String lineTxt = null;
				while((lineTxt = reader.readLine()) != null){
					result.append(lineTxt);
				}
				reader.close();
				inputStreamReader.close();
				return result.toString();
			}else{
				System.err.println("文件不存在");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	private static void writeToFile(String content){
		 /* 写入Txt文件 */  
        try {
			File writename = new File("C:\\Users\\Administrator\\Desktop\\output.txt"); // 相对路径，如果没有则要建立一个新的output。txt文件  
			writename.createNewFile(); // 创建新文件  
			BufferedWriter out = new BufferedWriter(new FileWriter(writename));  
			out.write(content); // \r\n即为换行  
			out.flush(); // 把缓存区内容压入文件  
			out.close(); // 最后记得关闭文件  
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	 public static String sortAndGetString(Map<String, Integer> map) {
	        List<String> strings = new LinkedList<String>();
	        List<Integer> integers = new LinkedList<Integer>();
	        for (String string : map.keySet()) {
	            strings.add(string);
	            integers.add(map.get(string));
	        }
	        for (int i = 0; i < integers.size(); i++) {
	            for (int j = i; j < integers.size(); j++) {
	                if (integers.get(i) < integers.get(j)) {
	                    swap(i, j, strings, integers);
	                }
	            }
	        }
	        StringBuilder result = new StringBuilder();
	        for (int i = 0; i < integers.size(); i++) {
//	            if (i == 0) {
//	                System.out.print("{");
//	            } else {
//	                System.out.print(",");
//	            }
//	            System.out.print(strings.get(i) + "=" + integers.get(i));
	            String s = strings.get(i) + "\t" + integers.get(i);
	            result.append(s+"\r\n");
//	            if (i == integers.size() - 1) {
//	                System.out.print("}");
//	            }
	        }
	        return result.toString();
	    }
	    private static void swap(int i, int j, List<String> strings, List<Integer> integers) {
	        String string = strings.get(i);
	        strings.set(i, strings.get(j));
	        strings.set(j, string);
	        Integer integer = integers.get(i);
	        integers.set(i, integers.get(j));
	        integers.set(j, integer);
	    }
}

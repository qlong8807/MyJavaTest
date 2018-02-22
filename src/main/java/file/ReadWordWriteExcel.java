package file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import superutil.thirdparty.excel.ExcelUtils;


/**
 * word中有N行数据，每行数据中的字段以空格拆开。把这些字段写入到Excel中。
 * 引入6个jar包：poi-3.8-20120326.jar；poi-ooxml-3.8-20120326.jar；poi-ooxml-schemas-3.8-20120326.jar；
 * poi-scratchpad-3.8-20120326.jar；xmlbeans-2.3.0.jar；dom4j-1.6.1.jar。
 * @author zyl
 * @date 2018年2月22日
 * @desc 
 */
public class ReadWordWriteExcel {
	public static void main(String[] args) {
		String readWord = readWord("/Users/apple/Documents/test/111.docx");
//		System.out.println(readWord);
		String[] split = readWord.split("\n");
		List<List<String[]>> list = new ArrayList<>();
		List<String[]> list2 = new ArrayList<>();
		for(int i=0;i<split.length;i++) {
			String line = split[i];
			if("".equals(line.trim())) continue;
			String[] words = line.split("\\s+");
			list2.add(words);
			for(String s:words) {
				System.out.println(s);
			}
		}
		list.add(list2);
		List<String[]> list3 = new ArrayList<>();
		String[] sa = {"abc","def"};
		list3.add(sa);
		list.add(list3);
		String[] strings = {"信息","ttt"};
		String[] strings2 = {"信息2","ttt2"};
		List<String[]> titles = new ArrayList<>();
		titles.add(strings);
		titles.add(strings2);
		String[] sheets = {"sheet1","s2"};
		try {
			ExcelUtils.writeToFile("/Users/apple/Documents/test/222.xlsx",sheets, titles, list);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static String readWord(String filePath) {
		String text = "";
		File file = new File(filePath);
		// 2003
		if (file.getName().endsWith(".doc")) {
			try {
				FileInputStream stream = new FileInputStream(file);
				WordExtractor word = new WordExtractor(stream);
				text = word.getText();
				// 去掉word文档中的多个换行
				text = text.replaceAll("(\\r\\n){2,}", "\r\n");
				text = text.replaceAll("(\\n){2,}", "\n");
				stream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else if (file.getName().endsWith(".docx")) { // 2007
			try {
				OPCPackage oPCPackage = POIXMLDocument.openPackage(filePath);
				XWPFDocument xwpf = new XWPFDocument(oPCPackage);
				POIXMLTextExtractor ex = new XWPFWordExtractor(xwpf);
				text = ex.getText();
				// 去掉word文档中的多个换行
				text = text.replaceAll("(\\r\\n){2,}", "\r\n");
				text = text.replaceAll("(\\n){2,}", "\n");
//				System.out.println("ok");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return text;
	}
	
}

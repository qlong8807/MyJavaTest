/**
 * 
 */
package web.html.htmlparser;

import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.util.NodeList;

/**
 * @author zyl
 * @date 2016年9月28日
 * 
 */
public class ParserTest {
	public static void main(String[] args) throws Exception {
		Parser parser = new Parser("http://www.dangdang.com");
		NodeList list = parser.parse(null);
		System.out.println(list.elementAt(0).toString());
		System.out.println(list.elementAt(1).toString());
		Node node = list.elementAt(1);
		NodeList sublist = node.getChildren();
		System.out.println(sublist.size());
	}
}

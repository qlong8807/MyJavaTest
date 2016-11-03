/**
 * 
 */
package web.html.jsoup;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
/**
 * jsoup解析html
 */
public class JsoupListLinks {
    public static void main(String[] args) throws IOException {
//        Validate.isTrue(args.length == 1, "usage: supply url to fetch");
//        String url = args[0];
    	String url = "http://www.baidu.com";
        print("Fetching %s...", url);
        Document doc = Jsoup.connect(url).get();
        Elements links = doc.select("a[href]");
        Elements media = doc.select("[src]");
        Elements imports = doc.select("link[href]");
        print("\nMedia: (%d)", media.size());
        for (Element src : media) {
            if (src.tagName().equals("img"))
                print(" * %s: <%s> %sx%s (%s)",
                        src.tagName(), src.attr("abs:src"), src.attr("width"), src.attr("height"),
                        trim(src.attr("alt"), 20));
            else
                print(" * %s: <%s>", src.tagName(), src.attr("abs:src"));
        }
        print("\nImports: (%d)", imports.size());
        for (Element link : imports) {
            print(" * %s <%s> (%s)", link.tagName(),link.attr("abs:href"), link.attr("rel"));
        }
        print("\nLinks: (%d)", links.size());
        for (Element link : links) {
            print(" * a: <%s>  (%s)", link.attr("abs:href"), trim(link.text(), 35));
        }
    }
    private static void print(String msg, Object... args) {
        System.out.println(String.format(msg, args));
    }
    private static String trim(String s, int width) {
        if (s.length() > width)
            return s.substring(0, width-1) + ".";
        else
            return s;
    }
    
    //从网络加载
   /* Document doc = Jsoup.connect("http://www.jb51.net")
    		  .data("query", "Java")
    		  .userAgent("Mozilla")
    		  .cookie("auth", "token")
    		  .timeout(3000)
    		  .post();
    String title = doc.title();
    //从文件加载
    File input = new File("/tmp/input.html");
    Document doc = Jsoup.parse(input, "UTF-8", "http://www.jb51.net/");
    Element content = doc.getElementById("content");
    Elements links = content.getElementsByTag("a");
    for (Element link : links) {
      String linkHref = link.attr("href");
      String linkText = link.text();
    }
    Elements links = doc.select("a[href]"); //带有href属性的a元素
    Elements pngs = doc.select("img[src$=.png]");
      //扩展名为.png的图片
    Element masthead = doc.select("div.masthead").first();
      //class等于masthead的div标签
    Elements resultLinks = doc.select("h3.r > a"); //在h3元素之后的a元素
    
    
    String html = "<p>An <a href='http://www.jb51.net/'><b>www.jb51.net</b></a> link.</p>";
    Document doc = Jsoup.parse(html);//解析HTML字符串返回一个Document实现
    Element link = doc.select("a").first();//查找第一个a元素
    String text = doc.body().text(); // "An www.jb51.net link"//取得字符串中的文本
    String linkHref = link.attr("href"); // "http://www.jb51.net/"//取得链接地址
    String linkText = link.text(); // "www.jb51.net""//取得链接地址中的文本
    String linkOuterH = link.outerHtml(); 
        // "<a href="http://www.jb51.net"><b>www.jb51.net</b></a>"
    String linkInnerH = link.html(); // "<b>www.jb51.net</b>"//取得链接内的html内容
*/}

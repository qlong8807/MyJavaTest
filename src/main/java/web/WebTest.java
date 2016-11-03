package web;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

public class WebTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			URL url = new URL(
					"http://train.qunar.com/stationToStation.htm?fromStation=%E6%B5%8E%E5%8D%97&toStation=%E7%83%9F%E5%8F%B0&date=2012-01-08");

			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			InputStream in = null;
			in = url.openStream();
			String content = pipe(in, "utf-8");

			System.out.println(content);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static String pipe(InputStream in, String charset) throws IOException {
		StringBuffer s = new StringBuffer();
		if (charset == null || "".equals(charset)) {
			charset = "utf-8";
		}
		String rLine = null;
		BufferedReader bReader = new BufferedReader(new InputStreamReader(in,
				charset));
		PrintWriter pw = null;

		FileOutputStream fo = new FileOutputStream("../index.html");
		OutputStreamWriter writer = new OutputStreamWriter(fo, "utf-8");
		pw = new PrintWriter(writer);
		while ((rLine = bReader.readLine()) != null) {
			String tmp_rLine = rLine;
			int str_len = tmp_rLine.length();
			if (str_len > 0) {
				s.append(tmp_rLine);
				pw.println(tmp_rLine);
				pw.flush();
			}
			tmp_rLine = null;
		}
		in.close();
		pw.close();
		return s.toString();
	}
}
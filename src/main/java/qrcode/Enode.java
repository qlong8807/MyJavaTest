package qrcode;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Hashtable;
import java.util.Map;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

public class Enode {

	public static void main(String[] args) throws Exception {
		String text = "http://www.baidu.com+hello";
		int width = 300;
		int height = 300;
		// 二维码的图片格式
		String format = "jpg";
		Map<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
		// 内容所使用编码
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		BitMatrix bitMatrix = new MultiFormatWriter().encode(text,
				BarcodeFormat.QR_CODE, width, height, hints);
		// 生成二维码
		File outputFile = new File("E:" + File.separator + "newQR.jpg");
		String filePath = System.getProperty("user.dir")+File.separator +"resourses"+File.separator+ "fire50.jpg";
		//从文件中读取BufferedImage
		BufferedImage logo = ImageIO.read(new File(filePath));
		//把BufferedImage写到文件中
		//ImageIO.write(logo, "jpg", new File("E:" + File.separator + "log.jpg"));
		
		//zxing自带有MatrixToImageWriter类，但是这里因为我要添加logo，所以自己写了一个
		MatrixToImageWriter.writeToFile(bitMatrix, format, outputFile, logo);
	}
}
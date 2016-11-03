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
		// ��ά���ͼƬ��ʽ
		String format = "jpg";
		Map<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
		// ������ʹ�ñ���
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		BitMatrix bitMatrix = new MultiFormatWriter().encode(text,
				BarcodeFormat.QR_CODE, width, height, hints);
		// ���ɶ�ά��
		File outputFile = new File("E:" + File.separator + "newQR.jpg");
		String filePath = System.getProperty("user.dir")+File.separator +"resourses"+File.separator+ "fire50.jpg";
		//���ļ��ж�ȡBufferedImage
		BufferedImage logo = ImageIO.read(new File(filePath));
		//��BufferedImageд���ļ���
		//ImageIO.write(logo, "jpg", new File("E:" + File.separator + "log.jpg"));
		
		//zxing�Դ���MatrixToImageWriter�࣬����������Ϊ��Ҫ���logo�������Լ�д��һ��
		MatrixToImageWriter.writeToFile(bitMatrix, format, outputFile, logo);
	}
}
package qrcode;

import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;

public class ImageUtil {
	/**
	 * Í¼Ïñ±ä»Ò²Ù×÷
	 * @param originalPic
	 * @return
	 */
	public final BufferedImage getGrayPicture(BufferedImage originalPic) {
		int imageWidth = originalPic.getWidth();
		int imageHeight = originalPic.getHeight();

		BufferedImage newPic = new BufferedImage(imageWidth, imageHeight,
				BufferedImage.TYPE_3BYTE_BGR);

		ColorConvertOp cco = new ColorConvertOp(
				ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
		cco.filter(originalPic, newPic);
		return newPic;
	}

}

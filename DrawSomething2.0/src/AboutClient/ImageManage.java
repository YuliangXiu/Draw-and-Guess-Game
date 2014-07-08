package AboutClient;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;

//参考博客http://blog.csdn.net/chenweionline/article/details/1728596
//通过网络传输编码压缩之后的图像

public class ImageManage implements Serializable{
	byte[] imageData = null;
	
	public ImageManage(BufferedImage image)
	{
		imageData = getCompressedImage(image);
	}
	
	public BufferedImage getBufferedImage()
	{
		BufferedImage image = getDecompressedImage(imageData);
		return image;
	}
	
	//将图片对象编码并存在字节数组中
	
	private byte[] getCompressedImage(BufferedImage image) {
		byte[] imageData = null;
		try
		{
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(image, "jpg", baos);
			imageData = baos.toByteArray();
		} catch (IOException ex)
		{
			imageData = null;
		}
		return imageData;
	}

	//从字节数组中解码出图片对象
	
	private BufferedImage getDecompressedImage(byte[] imageData) {
		try
		{
			ByteArrayInputStream bais = new ByteArrayInputStream(imageData);
			return ImageIO.read(bais);
		} catch (IOException ex)
		{
			return null;
		}
	}
}

package AboutClient;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;

//�ο�����http://blog.csdn.net/chenweionline/article/details/1728596
//ͨ�����紫�����ѹ��֮���ͼ��

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
	
	//��ͼƬ������벢�����ֽ�������
	
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

	//���ֽ������н����ͼƬ����
	
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

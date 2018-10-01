package han.util;


/**
 * 
 */


import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


/**
 * 
 * @author Administrator
 *
 */
public class Thumbnail 
{

	private BufferedImage img;
	
	
	private String extension;
	
	public Thumbnail()
	{
		
	}
	
	
	
	public Thumbnail(File f) throws IOException
	{
		img =  ImageIO.read(f);
		extension = getExtension(f);
	}
	
	
	
	public Thumbnail(String fileName) throws IOException
	{
		File imgFile = new File(fileName);
		img =  ImageIO.read(imgFile);
		extension = getExtension(imgFile);
	}
	
	
	
	public void read(File f) throws IOException
	{
		img =  ImageIO.read(f);
		extension = getExtension(f);
	}
	
	
	
	public void read(String fileName) throws IOException
	{
		File imgFile = new File(fileName);
		img =  ImageIO.read(imgFile);
		extension = getExtension(imgFile);
	}
	
	
	
	public int getWidth()
	{
		return img.getWidth();
	}
	
	
	public int getHeight()
	{
		return img.getHeight();
	}
	
	
	public String getExtension(File f)
	{
		String fileName = f.getName();
		int pos = fileName.lastIndexOf('.');
		return fileName.substring(++pos);
	}
	
	
	public BufferedImage getThumbnail(int width, int height)
	{
		 //메모리에 이미지 공간을 생성
		 BufferedImage thumb = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		 //BufferedImage로 부터 Graphics2D객체를 얻어냄
		 Graphics2D  g2 = thumb.createGraphics(); 
		 //메모리의 이미지공간에 원본 이미지를 가로 width, 세로 height 크기로 그린다.
		 g2.drawImage(img, 0, 0, width, height, null);
		 return thumb;
	}
	
	
	
	public void write(File f, int width, int height) throws IOException
	{
		BufferedImage thumb = getThumbnail(width, height);
		//메모리에 그린이미지를 파일로 저장 
		ImageIO.write(thumb, extension, f);
	}
	
	
	
	public void write(String fileName, int width, int height) throws IOException
	{
		BufferedImage thumb = getThumbnail(width, height);
		//메모리에 그린이미지를 파일로 저장 
		File f = new File(fileName);
		ImageIO.write(thumb, extension, f);
	}
}


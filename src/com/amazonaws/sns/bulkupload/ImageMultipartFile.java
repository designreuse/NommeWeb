package com.amazonaws.sns.bulkupload;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.springframework.web.multipart.MultipartFile;

public class ImageMultipartFile implements MultipartFile {
	
	private final byte[] imageBytes;
	private String fileName;
	
	public ImageMultipartFile(BufferedImage image, String fileName) throws IOException {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		ImageIO.write(image, "jpg", byteArrayOutputStream);
		this.imageBytes = byteArrayOutputStream.toByteArray();
		this.fileName = fileName;
	}

	@Override
	public byte[] getBytes() throws IOException {
		return imageBytes;
	}

	@Override
	public String getContentType() {
		return "jpg";
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return new ByteArrayInputStream(imageBytes);
	}

	@Override
	public String getName() {
		return fileName;
	}

	@Override
	public String getOriginalFilename() {
		return fileName;
	}

	@Override
	public long getSize() {
		return imageBytes.length;
	}

	@Override
	public boolean isEmpty() {
		if (imageBytes != null && imageBytes.length > 0) {
			return false;
		}
		return true;
	}

	@Override
	public void transferTo(File arg0) throws IOException, IllegalStateException {
		FileOutputStream outputStream = new FileOutputStream(arg0);
		outputStream.write(imageBytes);
		outputStream.close();
	}

}

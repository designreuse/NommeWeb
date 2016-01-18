package com.camut.utils;

import java.util.List;

import javax.imageio.ImageIO;

import com.jhlabs.image.QuantizeFilter;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class ImageBucketUtil {
	public ImageBucketUtil() {
		
	}
	
	/**
	 * @Title: getBucketImageUrls
	 * @Description: Gets the URLs for all the images in our Amazon image bucket.
	 * @return: List<String>
	 */
	private List<String> getBucketImageUrls() {
		// TODO: Get the list of image URLs from the Amazon image bucket.
		List<String> imageUrlList = new ArrayList<String>();
		imageUrlList.add("https://nomme-img-test.s3.amazonaws.com/71/0ba235c3-4d7f-4dc2-9d19-82315865420e.png");
		return imageUrlList;
	}
	
	/**
	 * @Title: downloadImage
	 * @Description: Downloads the requested image from the URL.
	 * @param imageUrl
	 * @return: BufferedImage
	 */
	private BufferedImage downloadImage(String imageUrl) {
		Image image = null;
		try {
		    URL downloadUrl = new URL(imageUrl);
		    image = ImageIO.read(downloadUrl);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return (BufferedImage)image;
	}
	
	/**
	 * @Title: uploadImage
	 * @Description: Uploads the given image to the given image URL for our Amazon image bucket.
	 * @param image
	 * @param imageUrl
	 */
	private void uploadImage(BufferedImage image, String imageUrl) {
		// TODO: Upload the image.
		// DEBUG: Write the image to a local file.
		File outputfile = new File("image.jpg");
		try {
			ImageIO.write(image, "jpg", outputfile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @Title: reduceColorDepth
	 * @Description: Reduces the color depth of the given image to be 8-bit.
	 * @param originalImage
	 * @return: BufferedImage
	 */
	private BufferedImage reduceColorDepth(BufferedImage originalImage) {
		// Create a new image for the modifications.
	    BufferedImage modifiedImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
		
		// Create buffers for the pixels.
		int[] inPixels = new int[originalImage.getWidth() * originalImage.getHeight() * 3];
		int[] outPixels = new int[originalImage.getWidth() * originalImage.getHeight() * 3];
		
		// Load the original image into the input buffer.
		originalImage.getRaster().getPixels(0, 0, originalImage.getWidth(), originalImage.getHeight(), inPixels);
		
		// Change the color depth.
		QuantizeFilter q = new QuantizeFilter();
		q.quantize(inPixels, outPixels, originalImage.getWidth(), originalImage.getHeight() * 3, 32, false, false);
		
		// Write the output buffer to the modified image.
		modifiedImage.getRaster().setPixels(0, 0, originalImage.getWidth(), originalImage.getHeight(), outPixels);
		
		return modifiedImage;
	}
	
	/**
	 * @Title: reduceColorDepthOfAllAmazonImages
	 * @Description: Reduces the color depth to be 8-bit for all the images found on the Amazon image bucket.
	 */
	public void reduceColorDepthOfAllAmazonImages() {
		System.out.println("Reducing color depth of all Amazon images");
		
		// Fetch the list of image URLs from the Amazon image bucket.
		System.out.println("Getting image URLs");
		List<String> imageUrlList = getBucketImageUrls();
		for (String imageUrl : imageUrlList) {
			System.out.println("Current image: " + imageUrl);
			
			// Download the image.
			System.out.println("Downloading image");
			BufferedImage originalImage = downloadImage(imageUrl);
			
			// Reduce the image's color depth.
			System.out.println("Reducing color depth");
			BufferedImage modifiedImage = originalImage;
			try {
				modifiedImage = reduceColorDepth(originalImage);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			// Upload the image.
			System.out.println("Uploading image");
			uploadImage(modifiedImage, imageUrl);
		}
		
		System.out.println("Done");
	}
}

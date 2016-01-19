package com.camut.utils;

import com.jhlabs.image.QuantizeFilter;
import javax.imageio.ImageIO;

import org.springframework.web.multipart.MultipartFile;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.UUID;

public class ImageBucketUtil {
	public ImageBucketUtil() {
		
	}
	
	/**
	 * @Title: getBucketImageUrls
	 * @Description: Gets the URLs for all the images in our Amazon image bucket.
	 * @return: List<String>
	 */
	private List<String> getBucketImageUrls() {
		// Get the image URLs from our Amazon bucket.
		List<String> imageUrlList = AWSUtil.getBucketImageUrlListFromS3SingleOperation();
		return imageUrlList;
	}
	
	/**
	 * @Title: downloadImage
	 * @Description: Downloads the requested image from the URL.
	 * @param imageUrl
	 * @return: BufferedImage
	 */
	private BufferedImage downloadImage(String imageUrl) {
		// Abort operation if arguments invalid.
		if (StringUtil.isEmpty(imageUrl)) {
			return null;
		}
		
		try {
		    URL downloadUrl = new URL(imageUrl);
		    Image image = ImageIO.read(downloadUrl);
		    return (BufferedImage)image;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @Title: uploadImage
	 * @Description: Uploads the given image to the given image URL for our Amazon image bucket.
	 * @param image
	 * @param imageUrl
	 */
	private void uploadImage(BufferedImage image, String imageUrl, boolean saveToLocal) {
		// Abort operation if arguments invalid.
		if (image == null || StringUtil.isEmpty(imageUrl)) {
			return;
		}
		
		try {
			// If saving the file locally, write to file.
			if (saveToLocal) {
				File outputfile = new File(imageUrl);
				ImageIO.write(image, "jpg", outputfile);
			}
			
			// TODO: Upload the image.
			//MultipartFile file = (MultipartFile) image;
			//AWSUtil.uploadImageToNommeS3SingleOperation(file, imageUrl);
		} catch (Exception e) {
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
		// Abort operation if arguments invalid.
		if (originalImage == null) {
			return null;
		}
		
		try {
			// Create a new image for the modifications.
		    BufferedImage modifiedImage = new BufferedImage(originalImage.getWidth(), originalImage.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
			
			// Create buffers for the pixels.
			int[] inPixels = new int[originalImage.getWidth() * originalImage.getHeight() * 3];
			int[] outPixels = new int[originalImage.getWidth() * originalImage.getHeight() * 3];
			
			// Load the original image into the input buffer.
			originalImage.getRaster().getPixels(0, 0, originalImage.getWidth(), originalImage.getHeight(), inPixels);
			
			// Change the color depth.
			QuantizeFilter q = new QuantizeFilter();
			q.quantize(inPixels, outPixels, originalImage.getWidth(), originalImage.getHeight() * 3, 64, false, false);
			
			// Write the output buffer to the modified image.
			modifiedImage.getRaster().setPixels(0, 0, originalImage.getWidth(), originalImage.getHeight(), outPixels);
			
			return modifiedImage;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
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
			if (originalImage == null) {
				continue;
			}
			
			// Reduce the image's color depth.
			System.out.println("Reducing color depth");
			BufferedImage modifiedImage = originalImage;
			modifiedImage = reduceColorDepth(originalImage);
			if (modifiedImage == null) {
				continue;
			}
			
			// Upload the image.
			System.out.println("Uploading image");
			uploadImage(modifiedImage, imageUrl, true);
		}
		
		System.out.println("Done");
	}
}

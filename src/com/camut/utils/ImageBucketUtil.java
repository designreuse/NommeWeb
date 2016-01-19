package com.camut.utils;

import com.amazonaws.sns.bulkupload.ImageMultipartFile;
import com.jhlabs.image.QuantizeFilter;
import javax.imageio.ImageIO;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.List;

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
	 * @Title: writeImageToFile
	 * @Description: Writes the image to a local file.
	 * @param image
	 * @param imageUrl
	 * @return: boolean
	 */
	private boolean writeImageToFile(BufferedImage image, String imageUrl) {
		// Abort operation if arguments invalid.
		if (image == null || StringUtil.isEmpty(imageUrl)) {
			return false;
		}
		
		try {
			// Get the filename from the URL.
			String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
			
			// Write the image to a local file.
			File outputfile = new File(fileName);
			ImageIO.write(image, "jpg", outputfile);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * @Title: uploadImage
	 * @Description: Uploads the given image to the given image URL for our Amazon image bucket.
	 * @param image
	 * @param imageUrl
	 * @return: boolean
	 */
	private boolean uploadImage(BufferedImage image, String imageUrl) {
		// Abort operation if arguments invalid.
		if (image == null || StringUtil.isEmpty(imageUrl)) {
			return false;
		}
		
		try {
			// Change the image URL to strip off the bucket name.  We only need the restaurant ID and image UUID.
			int indexForFileUuidSlash = imageUrl.lastIndexOf("/");
			if (indexForFileUuidSlash == -1) {
				return false;
			}
			int indexForRestaurantIdSlash = imageUrl.lastIndexOf("/", indexForFileUuidSlash - 1);
			if (indexForRestaurantIdSlash == -1) {
				return false;
			}
			String imagePath = imageUrl.substring(indexForRestaurantIdSlash + 1);
			
			// Upload the image to Amazon.
			ImageMultipartFile imageMultipartFile = new ImageMultipartFile(image, imagePath);
			AWSUtil.uploadImageToNommeS3SingleOperation(imageMultipartFile, imagePath);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
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
		System.out.println("Reducing color depth of all Amazon images.");
		
		// Fetch the list of image URLs from the Amazon image bucket.
		System.out.println("Getting image URLs.");
		List<String> imageUrlList = getBucketImageUrls();
		for (String imageUrl : imageUrlList) {
			System.out.println("Current image: " + imageUrl);
			
			// Download the image.
			System.out.println("Downloading image.");
			BufferedImage originalImage = downloadImage(imageUrl);
			if (originalImage == null) {
				System.out.println("Failed to download image.");
				continue;
			}
			
			// Reduce the image's color depth.
			System.out.println("Reducing color depth.");
			BufferedImage modifiedImage = originalImage;
			modifiedImage = reduceColorDepth(originalImage);
			if (modifiedImage == null) {
				System.out.println("Failed to reduce color depth.");
				continue;
			}
			
			// Write the original image locally.
			System.out.println("Writing original image to local storage.");
			boolean imageWritten = writeImageToFile(originalImage, imageUrl);
			if (imageWritten != true) {
				System.out.println("Failed to write original image to local storage.");
				continue;
			}
			
			// Upload the image.
			System.out.println("Uploading modified image to Amazon S3.");
			boolean uploadDone = uploadImage(modifiedImage, imageUrl);
			if (uploadDone != true) {
				System.out.println("Failed to upload modified image to Amazon S3.");
			}
		}
		
		System.out.println("Done");
	}
}

package com.amazonaws.sns.bulkupload;

import com.camut.utils.ImageBucketUtil;

public class BatchModifyUploadedImages {
	public static void main(String[] args) {
		// Run the color depth modification utility.
		ImageBucketUtil imageBucketUtil = new ImageBucketUtil();
		imageBucketUtil.reduceColorDepthOfAllAmazonImages();
	}
}

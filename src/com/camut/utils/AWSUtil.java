package com.camut.utils;

import java.io.IOException;
import java.io.InputStream;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import org.springframework.web.multipart.MultipartFile;

public final class AWSUtil {
	public static String AWS_ACCESS_KEY_VAR = "AWS_ACCESS_KEY";
	public static String AWS_SECRET_KEY_VAR = "AWS_SECRET_KEY";
	public static String AWS_IMAGES_BUCKET_VAR = "AWS_NOMME_IMAGES_BUCKET";
	
	public static String uploadImageToNommeS3SingleOperation(MultipartFile file, String awsKeyName) throws IllegalArgumentException
	{
		String bucketName = getSystemVariableOrProperty(AWS_IMAGES_BUCKET_VAR);	
		if (bucketName == null)
		{
			throw new IllegalArgumentException("AWS S3 bucket not defined. The environment variable " + AWS_IMAGES_BUCKET_VAR + " must be set to an S3 bucket.");
		}
		
		return doUploadFileToS3SingleOperation(file, bucketName, awsKeyName);
	}
	
	public static String doUploadFileToS3SingleOperation(MultipartFile file, String bucketName, String awsKeyName) throws IllegalArgumentException
	{
		String access_key = getSystemVariableOrProperty(AWS_ACCESS_KEY_VAR);	
		if (access_key == null)
		{
			throw new IllegalArgumentException("AWS IAM access key not defined. The environment variable " + AWS_ACCESS_KEY_VAR + " must be set to access AWS services.");
		}
		
		String private_key = getSystemVariableOrProperty(AWS_SECRET_KEY_VAR);	
		if (private_key == null)
		{
			throw new IllegalArgumentException("AWS IAM secret key not defined. The environment variable " + AWS_SECRET_KEY_VAR + " must be set to access AWS services.");
		}
		
        AmazonS3Client s3client = new AmazonS3Client(new BasicAWSCredentials(access_key, private_key));
        try {
            InputStream stream = file.getInputStream();
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());

            s3client.putObject(new PutObjectRequest(bucketName, awsKeyName, stream, metadata).withCannedAcl(CannedAccessControlList.PublicRead));            
            return s3client.getResourceUrl(bucketName, awsKeyName);
         } catch (AmazonServiceException ase) {
        	 Log4jUtil.info("Caught an AmazonServiceException, which " +
            		"means your request made it " +
            		"to Amazon S3, but was rejected with an error response" +
                    " for some reason.");
        	 Log4jUtil.info("Error Message:    " + ase.getMessage());
        	 Log4jUtil.info("HTTP Status Code: " + ase.getStatusCode());
        	 Log4jUtil.info("AWS Error Code:   " + ase.getErrorCode());
        	 Log4jUtil.info("Error Type:       " + ase.getErrorType());
        	 Log4jUtil.info("Request ID:       " + ase.getRequestId());
        } catch (AmazonClientException ace) {
        	Log4jUtil.info("Caught an AmazonClientException, which " +
            		"means the client encountered " +
                    "an internal error while trying to " +
                    "communicate with S3, " +
                    "such as not being able to access the network.");
        	Log4jUtil.info("Error Message: " + ace.getMessage());
        } catch (IOException e) {
        	Log4jUtil.info("Cannot generate stream from file");
        	Log4jUtil.info("Error Message: " + e.getMessage());
        }
        
		return null;
	}
		
	private static String getSystemVariableOrProperty(String variable)
	{	
		String var = System.getenv(variable);

		//// Check environment variables
		if (var == null)
		{
			//// Check Java system properties
			var = System.getProperty(variable);
		}
		
		return var;
	}
}

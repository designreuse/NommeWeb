package com.camut.util;

import static org.junit.Assert.*;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.camut.utils.AWSUtil;
import com.camut.utils.StringUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring.xml","classpath*:spring-hibernate.xml"})
public class AWSUtilTest {

	@Test
	public void uploadImageToNommeS3SingleOperation() {
		MockMultipartFile file = new MockMultipartFile("testimage", "testimage.png", "image/png", "abcd".getBytes());
        String imgtype = StringUtil.getFileExtension(file.getOriginalFilename());
        
        String name = UUID.randomUUID().toString();
        String id = "12345";
		String keyName = "test/" + id + "/" + name + "." + imgtype;
		
		String bucket = System.getenv(AWSUtil.AWS_IMAGES_BUCKET_VAR);
		assertNotNull(bucket);
		
		String url = AWSUtil.uploadImageToNommeS3SingleOperation(file, keyName);
		assertNotNull(url);
		
		System.out.println("URL: " + url);
		assertEquals("https://" + bucket + ".s3.amazonaws.com/test/" + id + "/" + name + ".png", url);
	}
}

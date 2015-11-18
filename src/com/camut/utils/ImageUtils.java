package com.camut.utils;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 袁友林
 *
 */
public final class ImageUtils {
	
	
	private static String ctxPath="";
	private static String pathname="";
	
	//中等大小图片
	private static int bWidth=800;
	private static int bHeight=800;
	

	
	/**
	 * 保存图片
	 * @param file
	 * @param request
	 * @return
	 */
	public static String saveImage(MultipartFile file,HttpServletRequest request,String tmpDir,boolean flag){
		// 获得文件名：  
        String filename = file.getOriginalFilename();  
        String imgtype = filename.substring(filename.lastIndexOf("."));         
        // 获取路径  
        ctxPath = request.getSession().getServletContext().getRealPath("/");
        File dirPathImg = new File(ctxPath);  
        if (!dirPathImg.exists()) {  
        	dirPathImg.mkdir();  
        } 
        
        pathname =System.currentTimeMillis()+StringUtil.getRandom()+imgtype;// 存放路径  
        // 创建文件  
       
        if(StringUtil.isNotEmpty(tmpDir)){
        	 ctxPath=ctxPath+"/"+tmpDir;
        }
        File dirPath = new File(ctxPath);  
        if (!dirPath.exists()) {  
            dirPath.mkdir();  
        }  
       
        File uploadFile = new File(ctxPath+"/"+pathname);  
        try {
        	
			FileCopyUtils.copy(file.getBytes(), uploadFile);			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        //生成
        if(flag){
        	resize(ctxPath+"\\"+pathname,  bHeight,bWidth, false,imgtype);
        }
        if(StringUtil.isNotEmpty(tmpDir)){
        	return ctxPath+"/"+tmpDir+"/"+pathname;
        }
		return ctxPath+pathname;
	}
	
	public static boolean deleteImage(String filePath){
		File file = new File(filePath);
		if (file.exists() && file.isFile()) {  // 不存在返回 false  
			file.delete(); 
	        return true;  
	    }
		return false;
	}
	
	/**
     * 保存图片
     * @param file
     * @param request
     * @param path 指定的保存文件夹
     * @param tmpDir 需创建的文件夹，可多级
     * @param flag 是否创建多尺寸
     * @return
     */
    public static String saveImage(MultipartFile file,HttpServletRequest request,String path,String tmpDir,boolean flag) {
        String result = "";
        // 获得文件名：
        String filename = file.getOriginalFilename();
        String imgtype = filename.substring(filename.lastIndexOf("."));
     

        // 配置路径
        path = StringUtil.stripEnd(path,"/");
        // 实际路径
        String realPath = request.getSession().getServletContext().getRealPath(path);
        // 分类路径
        tmpDir = StringUtil.stripEnd(tmpDir,"/");
        // 存放文件
        pathname = System.currentTimeMillis()+StringUtil.getRandom()+imgtype;// 存放路径  

        if (StringUtil.isNotEmpty(realPath)) {
            // 创建文件夹
            if(StringUtil.isNotEmpty(tmpDir)){
                realPath=realPath+"/"+tmpDir;
            }
           // realPath = realPath + "/" + pathname;
            File dirPath = new File(realPath);
            if (!dirPath.exists()) {
                dirPath.mkdirs();
            }
            // 创建文件
            final String fullPath = realPath+"/"+pathname;
            File uploadFile = new File(fullPath);
            try {
                FileCopyUtils.copy(file.getBytes(), uploadFile);
               // final String pressPath = request.getSession().getServletContext().getRealPath("/WEB-INF/images/resource/fcp_shuiyin.png");
               // pressImage(pressPath, fullPath, 10, 5, 0.7f);
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 生成多尺寸
            if(flag){
                ctxPath = realPath;
                resize(fullPath,  bHeight,bWidth, false,imgtype);
                ctxPath = "";
            }
            // 返回路径
                result = path+"/"+tmpDir+"/"+pathname;
        }
        return result;
    }
	
	/**
	 * 缩放
	 * @param filePath 图片路径
	 * @param height 高度
	 * @param width 宽度
	 * @param bb 比例不对时是否需要补白
	 */
	public static void resize(String filePath, int height, int width, boolean bb,String imgtype) {
		try {
			double ratio = 0.0; //缩放比例 
			File f = new File(filePath);
			BufferedImage bi = ImageIO.read(f);
			Image itemp = bi.getScaledInstance(width, height, bi.SCALE_SMOOTH);
			
			//计算比例
			if ((bi.getHeight() > height) || (bi.getWidth() > width)) {
				if (bi.getHeight() > bi.getWidth()) {
					ratio = (new Integer(height)).doubleValue() / bi.getHeight();
				} else {
					ratio = (new Integer(width)).doubleValue() / bi.getWidth();
				}
				AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(ratio, ratio), null);
				itemp = op.filter(bi, null);
			}
			if (bb) {
				BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
				Graphics2D g = image.createGraphics();
				g.setColor(Color.white);
				g.fillRect(0, 0, width, height);
				if (width == itemp.getWidth(null))
					g.drawImage(itemp, 0, (height - itemp.getHeight(null)) / 2, itemp.getWidth(null), itemp.getHeight(null), Color.white, null);
				else
					g.drawImage(itemp, (width - itemp.getWidth(null)) / 2, 0, itemp.getWidth(null), itemp.getHeight(null), Color.white, null);
				g.dispose();
				itemp = image;
			}
			//String tmpPath=width+"X"+height;
			File file=new File(ctxPath+"\\"+pathname); 
			ByteArrayOutputStream bos = new ByteArrayOutputStream(); 
			BufferedImage bufImg = new BufferedImage(itemp.getWidth(null), itemp.getHeight(null),BufferedImage.TYPE_INT_RGB);   
	         Graphics g = bufImg .createGraphics();   
	         g.drawImage(itemp, 0, 0, null);   
	         g.dispose(); 
			ImageIO.write(bufImg, imgtype.replace(".", ""), bos);
			FileCopyUtils.copy(bos.toByteArray(), file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 图片水印
	 * @param pressImg 水印图片
	 * @param targetImg 目标图片
	 * @param x 修正值 默认在中间
	 * @param y 修正值 默认在中间
	 * @param alpha 透明度
	 */
	public final static void pressImage(String pressImg, String targetImg, int x, int y, float alpha) {
		try {
			File img = new File(targetImg);
			Image src = ImageIO.read(img);
			int wideth = src.getWidth(null);
			int height = src.getHeight(null);
			BufferedImage image = new BufferedImage(wideth, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = image.createGraphics();
			g.drawImage(src, 0, 0, wideth, height, null);
			//水印文件
			Image src_biao = ImageIO.read(new File(pressImg));
			int wideth_biao = src_biao.getWidth(null);
			int height_biao = src_biao.getHeight(null);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
			g.drawImage(src_biao, (wideth - wideth_biao) / 2, (height - height_biao) / 2, wideth_biao, height_biao, null);
			//水印文件结束
			g.dispose();
			ImageIO.write((BufferedImage) image, "jpg", img);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 文字水印
	 * @param pressText 水印文字
	 * @param targetImg 目标图片
	 * @param fontName 字体名称
	 * @param fontStyle 字体样式
	 * @param color 字体颜色
	 * @param fontSize 字体大小
	 * @param x 修正值
	 * @param y 修正值
	 * @param alpha 透明度
	 */
	public static void pressText(String pressText, String targetImg, String fontName, int fontStyle, Color color, int fontSize, int x, int y, float alpha) {
		try {
			File img = new File(targetImg);
			Image src = ImageIO.read(img);
			int width = src.getWidth(null);
			int height = src.getHeight(null);
			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = image.createGraphics();
			g.drawImage(src, 0, 0, width, height, null);
			g.setColor(color);
			g.setFont(new Font(fontName, fontStyle, fontSize));
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
			g.drawString(pressText, (width - (getLength(pressText) * fontSize)) / 2 + x, (height - fontSize) / 2 + y);
			g.dispose();
			ImageIO.write((BufferedImage) image, "jpg", img);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	

	public static int getLength(String text) {
		int length = 0;
		for (int i = 0; i < text.length(); i++) {
			if (new String(text.charAt(i) + "").getBytes().length > 1) {
				length += 2;
			} else {
				length += 1;
			}
		}
		return length / 2;
	}
	
	
	
	/**
     * 保存图片
     * @param file
     * @param request
     * @param path 指定的保存文件夹
     * @param tmpDir 需创建的文件夹，可多级
     * @param flag 是否创建多尺寸
     * @return
     */
    public static String saveImageWithOrginalImageName(MultipartFile file,HttpServletRequest request,String path,String tmpDir,String filename,boolean flag) {
        String result = "";
        // 获得文件名：
       // String filename = file.getOriginalFilename();
        String imgtype = filename.substring(filename.lastIndexOf("."));
       /* String base = "0123456789";   
	    Random random = new Random();   
	    StringBuffer sb = new StringBuffer();   
	    for (int i = 0; i <6; i++) {   
	        int number = random.nextInt(base.length());   
	        sb.append(base.charAt(number));   
	    }  */ 
        /*String strTime = new Date().getTime()+"";
	    filename = filename.substring(0, filename.lastIndexOf("."));
	    filename = filename + ("-"+strTime) + imgtype; */
		


        // 配置路径
        path = StringUtil.stripEnd(path,"/");
        // 实际路径
        String realPath = request.getSession().getServletContext().getRealPath(path);
        // 分类路径
        tmpDir = StringUtil.stripEnd(tmpDir,"/");
        // 存放文件
        pathname = filename;// 存放路径  

        if (StringUtil.isNotEmpty(realPath)) {
            // 创建文件夹
            if(StringUtil.isNotEmpty(tmpDir)){
                realPath=realPath+tmpDir;
            }
           // realPath = realPath + "/" + pathname;
            File dirPath = new File(realPath);
            if (!dirPath.exists()) {
                dirPath.mkdirs();
            }
            // 创建文件
            final String fullPath = realPath+"/"+filename;
            File uploadFile = new File(fullPath);
            try {
                FileCopyUtils.copy(file.getBytes(), uploadFile);
               // final String pressPath = request.getSession().getServletContext().getRealPath("/WEB-INF/images/resource/fcp_shuiyin.png");
               // pressImage(pressPath, fullPath, 10, 5, 0.7f);
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 生成多尺寸
            if(flag){
                ctxPath = realPath;
                writeImage(fullPath,  bHeight,bWidth, false,imgtype);
                ctxPath = "";
            }
           
                result = path+tmpDir+"/"+filename;
        }
        return result;
    }
    
    
    
    public static void writeImage(String filePath, int height, int width, boolean bb,String imgtype) {
		try {
			File f = new File(filePath);
			BufferedImage bi = ImageIO.read(f);
			File file=new File(ctxPath+"\\"+pathname); 
			ByteArrayOutputStream bos = new ByteArrayOutputStream(); 
			ImageIO.write(bi, imgtype.replace(".", ""), bos);
			FileCopyUtils.copy(bos.toByteArray(), file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
}

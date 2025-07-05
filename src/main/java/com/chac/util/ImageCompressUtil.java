package com.chac.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Base64;

@Slf4j
@Component
public class ImageCompressUtil {
    public static void main(String[] args) {
        try {
            // 测试压缩图片
//            String inputFilePath = "/Users/zhangbofeng/高灯work_space/英明头像_1.png"; // 输入图片路径
//            String outputFilePath = "/Users/zhangbofeng/高灯work_space/英明头像_afterCompress.png"; // 输出压缩后的图片路径
//            float quality = 0.6f; // 压缩质量
//
//            compressImage(inputFilePath, outputFilePath, quality);
//            log.info("图片压缩成功，输出路径: {}", outputFilePath);
            String fileUrl = "https://hopped-user-upload-1258944054.cos.ap-guangzhou.myqcloud.com/20250111120707/1dce7aa6-ed8f-4979-a202-c3f1815aa140.jpg";
            String outputFilePath = "/Users/zhangbofeng/高灯work_space/英明头像_compressImageUrl.jpg"; // 输出压缩后的图片路径
            float quality = 0.6f; // 压缩质量
            compressImageFromUrl(fileUrl, outputFilePath, quality);
        } catch (IOException e) {
            log.error("图片压缩失败", e);
        }
    }

    /**
     * 指定URL图片连接 并压缩
     */
    public static void compressImageFromUrl(String imageUrl, String outputFilePath, float quality) throws IOException {
        // 读取图片文件
        BufferedImage bufferedImage = ImageIO.read(new URL(imageUrl));

        // 获取图片的写入器
        ImageWriter imageWriter = ImageIO.getImageWritersByFormatName("jpg").next();

        // 设置输出流
        ImageOutputStream imageOutputStream = ImageIO.createImageOutputStream(new File(outputFilePath));
        imageWriter.setOutput(imageOutputStream);

        // 设置压缩参数
        ImageWriteParam imageWriteParam = imageWriter.getDefaultWriteParam();
        imageWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        imageWriteParam.setCompressionQuality(quality); // 压缩质量，0.0f - 1.0f

        // 创建 IIOImage 对象
        IIOImage iioImage = new IIOImage(bufferedImage, null, null);

        // 写出压缩后的图片
        imageWriter.write(null, iioImage, imageWriteParam);

        // 关闭资源
        imageOutputStream.close();
        imageWriter.dispose();
        log.info("图片压缩成功，输出路径: {}", outputFilePath);
    }

    /**
     * 压缩图片
     *
     * @param inputFilePath  输入图片文件路径
     * @param outputFilePath 输出压缩后的图片文件路径
     * @param quality        压缩质量，范围0-1 正常传0.6就行，会把80k的图片压缩为10k，且质量不变
     */
    public static void compressImage(String inputFilePath, String outputFilePath, float quality) throws IOException {
        // 读取图片文件
        File inputFile = new File(inputFilePath);
        BufferedImage bufferedImage = ImageIO.read(inputFile);

        // 获取图片的写入器
        ImageWriter imageWriter = ImageIO.getImageWritersByFormatName("jpg").next();

        // 设置输出流
        ImageOutputStream imageOutputStream = ImageIO.createImageOutputStream(new File(outputFilePath));
        imageWriter.setOutput(imageOutputStream);

        // 设置压缩参数
        ImageWriteParam imageWriteParam = imageWriter.getDefaultWriteParam();
        imageWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        imageWriteParam.setCompressionQuality(quality); // 压缩质量，0.0f - 1.0f

        // 创建 IIOImage 对象
        IIOImage iioImage = new IIOImage(bufferedImage, null, null);

        // 写出压缩后的图片
        imageWriter.write(null, iioImage, imageWriteParam);

        // 关闭资源
        imageOutputStream.close();
        imageWriter.dispose();
    }

    /**
     * 压缩图片 - base64
     *
     * @param base64Input 图片的base64编码
     * @param quality     压缩质量，范围0-1 正常传0.6就行，会把80k的图片压缩为10k，且质量不变
     * @return 压缩后的图片的base64编码
     */
    public static String compressImageBase64(String base64Input, float quality) throws IOException {
        // 1. Base64解码为BufferedImage
        byte[] imageBytes = Base64.getDecoder().decode(base64Input);
        ByteArrayInputStream bais = new ByteArrayInputStream(imageBytes);
        BufferedImage bufferedImage = ImageIO.read(bais);

        // 2. 获取图片写入器
        ImageWriter imageWriter = ImageIO.getImageWritersByFormatName("jpg").next();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageOutputStream ios = ImageIO.createImageOutputStream(baos);
        imageWriter.setOutput(ios);

        // 3. 设置压缩参数
        ImageWriteParam imageWriteParam = imageWriter.getDefaultWriteParam();
        imageWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        imageWriteParam.setCompressionQuality(quality);

        // 4. 写出压缩后的图片
        IIOImage iioImage = new IIOImage(bufferedImage, null, null);
        imageWriter.write(null, iioImage, imageWriteParam);

        // 5. 关闭资源
        ios.close();
        imageWriter.dispose();

        // 6. 输出Base64
        byte[] compressedBytes = baos.toByteArray();
        return Base64.getEncoder().encodeToString(compressedBytes);
    }
}

package com.wr.common.customize.util;

import cn.hutool.core.io.resource.ClassPathResource;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.wr.common.core.utils.StringUtils;
import com.wr.common.core.utils.sign.Base64;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import sun.font.FontDesignMetrics;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.Hashtable;

/**
 * @Author: RainCity
 * @Date: 2021-07-21 17:59:11
 * @Desc: 二维码生成
 */
public class QrCodeUtils extends BaseUtils {
    private static final Logger log = LoggerFactory.getLogger(QrCodeUtils.class);

    private static final String CHARSET = "utf-8";
    private static final String FORMAT_NAME = "JPG";
    /**
     * 二维码尺寸
     */
    private static final Integer QRCODE_SIZE = 380;
    /**
     * LOGO宽度
     */
    private static final Integer WIDTH = 100;
    /**
     * LOGO高度
     */
    private static final Integer HEIGHT = 100;

    private static BufferedImage createImage(String content, String imgPath, boolean needCompress) throws Exception {
        Hashtable hints = new Hashtable();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = new MultiFormatWriter()
                .encode(content, BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE, hints);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height + 10, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        if (imgPath == null || "".equals(imgPath)) {
            return image;
        }
        // 插入图片
        insertImage(image, imgPath, needCompress);
        return image;
    }

    private static BufferedImage createImage(String content, String imgPath, boolean needCompress, int wordHeight) throws Exception {
        Hashtable hints = new Hashtable();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = new MultiFormatWriter()
                .encode(content, BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE, hints);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height + wordHeight, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        if (imgPath == null || "".equals(imgPath)) {
            return image;
        }
        // 插入图片
        insertImage(image, imgPath, needCompress);
        return image;
    }

    private static void insertImage(BufferedImage source, String imgPath, boolean needCompress) throws Exception {
        File file = new File(imgPath);
        if (!file.exists()) {
            log.error(imgPath + "   该文件不存在！");
            return;
        }
        Image src = ImageIO.read(new File(imgPath));
        int width = src.getWidth(null);
        int height = src.getHeight(null);
        // 压缩LOGO
        if (needCompress) {
            if (width > WIDTH) {
                width = WIDTH;
            }
            if (height > HEIGHT) {
                height = HEIGHT;
            }
            Image image = src.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            // 绘制缩小后的图
            g.drawImage(image, 0, 0, null);
            g.dispose();
            src = image;
        }
        // 插入LOGO
        Graphics2D graph = source.createGraphics();
        int x = (QRCODE_SIZE - width) / 2;
        int y = (QRCODE_SIZE - height) / 2;
        graph.drawImage(src, x, y, width, height, null);
        Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
        graph.setStroke(new BasicStroke(3f));
        graph.draw(shape);
        graph.dispose();
    }

    /**
     * 生成二维码--方法1
     *
     * @param content      存放在二维码中的内容
     * @param imgPath      logo的路径及名称
     * @param destPath     生成的二维码的路径
     * @param needCompress 是否需要压缩
     * @return 生成的二维码的名称
     */
    public static String encode(String content, String imgPath, String destPath, boolean needCompress) {
        BufferedImage image;
        try {
            image = createImage(content, imgPath, needCompress);
            mkdirs(destPath);
            //若destPath仅仅是文件夹
            /*String qrPath = destPath+"/QR"+System.currentTimeMillis()+".jpg";
            ImageIO.write(image, FORMAT_NAME, new File(qrPath));
            return qrPath;*/

            //若destPath带有文件名
            File file = new File(destPath);
            ImageIO.write(image, FORMAT_NAME, file);
            return file.getName();
        } catch (Exception e) {
            log.error("二维码生成失败");
            return null;
        }
    }

    public static void mkdirs(String destPath) {
        File file = new File(destPath);
        // 当文件夹不存在时，mkdirs会自动创建多层目录，区别于mkdir．(mkdir如果父目录不存在则会抛出异常)
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }
    }

    /**
     * 生成二维码
     *
     * @param content      存放在二维码中的内容
     * @param imgPath      logo的路径及名称
     * @param needCompress 是否需要压缩
     * @return
     * @throws Exception
     */
    public static BufferedImage encode(String content, String imgPath, boolean needCompress) throws Exception {

        return createImage(content, imgPath, needCompress);
    }

    /**
     * 生成二维码
     * 不需要压缩
     *
     * @param content  存放在二维码中的内容
     * @param imgPath  logo的路径及名称
     * @param destPath 生成的二维码的路径
     */
    public static String encode(String content, String imgPath, String destPath) {

        return encode(content, imgPath, destPath, false);
    }

    /**
     * 生成二维码
     * 无logo，不需要压缩
     *
     * @param content  存放在二维码中的内容
     * @param destPath 生成的二维码的路径
     */
    public static String encode(String content, String destPath) {

        return encode(content, null, destPath, false);
    }

    /**
     * 生成二维码--方法2
     * 无输出路径
     *
     * @param content      存放在二维码中的内容
     * @param imgPath      logo的路径及名称
     * @param output       输出流
     * @param needCompress 是否需要压缩
     * @throws Exception
     */
    public static void encode(String content, String imgPath,
                              OutputStream output, boolean needCompress) throws Exception {
        BufferedImage image = createImage(content, imgPath, needCompress);
        ImageIO.write(image, FORMAT_NAME, output);
    }

    /**
     * 生成二维码
     * 无logo,无生成路径，不需要压缩
     *
     * @param content 存放在二维码中的内容
     * @param output  输出流
     * @throws Exception
     */
    public static void encode(String content, OutputStream output) throws Exception {
        encode(content, null, output, false);
    }

    /**
     * 根据二维码路径解析二维码
     *
     * @param path
     * @return java.lang.String
     */
    public static String decode(String path) throws Exception {
        return decode(new File(path));
    }

    /**
     * 根据二维码解析二维码
     *
     * @param file
     * @return java.lang.String
     */
    public static String decode(File file) throws Exception {
        BufferedImage image = ImageIO.read(file);
        if (image == null) {
            return null;
        }
        BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        Result result;
        Hashtable hints = new Hashtable();
        hints.put(DecodeHintType.CHARACTER_SET, CHARSET);
        result = new MultiFormatReader().decode(bitmap, hints);
        return result.getText();
    }

    /**
     * 初始化二维码 MultipartFile
     *
     * @param content  二维码内容
     * @param logoPath logo路径
     * @return
     * @throws Exception
     */
    private static MultipartFile initMultipartFile(String content, String logoPath) throws Exception {
        //二维码名称
        String qrName = "qr" + System.currentTimeMillis() + ".jpg";
        //得到BufferedImage对象
        BufferedImage bufferedImage = createImage(content, logoPath, true);
        //创建一个ByteArrayOutputStream
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        //把BufferedImage写入ByteArrayOutputStream
        ImageIO.write(bufferedImage, "jpg", os);
        //ByteArrayOutputStream转成InputStream
        InputStream input = new ByteArrayInputStream(os.toByteArray());
        //InputStream转成MultipartFile
        return new MockMultipartFile("qrFile", qrName, "text/plain", input);
    }

    /**
     * 生成二维码，带底部文字
     *
     * @param content    内容
     * @param logoPath   log地址
     * @param bottomText 底部文字
     * @return org.springframework.web.multipart.MultipartFile
     * @author yx
     * @date 2022/11/24 9:54
     */
    private static MultipartFile initMultipartFile(String content, String logoPath, String bottomText) throws Exception {
        //二维码名称
        String qrName = "qr" + System.currentTimeMillis() + ".jpg";

        int height = 0;
        if (StringUtils.hasText(bottomText)) {
            height = 10;
        }

        //得到BufferedImage对象
        BufferedImage bufferedImage = createImage(content, logoPath, true, height);
        // 判断是否添加底部文字
        if (StringUtils.hasText(bottomText)) {
            addFontImage(bufferedImage, bottomText);
        }

        //创建一个ByteArrayOutputStream
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        //把BufferedImage写入ByteArrayOutputStream
        ImageIO.write(bufferedImage, "jpg", os);
        //ByteArrayOutputStream转成InputStream
        InputStream input = new ByteArrayInputStream(os.toByteArray());
        //InputStream转成MultipartFile
        return new MockMultipartFile("qrFile", qrName, "text/plain", input);
    }

    /**
     * 二维码底部添加文字
     *
     * @param source      二维码图片
     * @param declareText 底部文字
     * @author yx
     * @date 2022/11/24 9:52
     */
    private static void addFontImage(BufferedImage source, String declareText) {
        //生成image
        int defineWidth = QRCODE_SIZE;
        int defineHeight = 20;
        BufferedImage textImage = new BufferedImage(defineWidth, defineHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D) textImage.getGraphics();
        //开启文字抗锯齿
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setBackground(Color.WHITE);
        g2.clearRect(0, 0, defineWidth, defineHeight);
        g2.setPaint(Color.BLACK);
        FontRenderContext context = g2.getFontRenderContext();
        //部署linux需要注意 linux无此字体会显示方块
        Font font = new Font("微软雅黑", Font.BOLD, 15);
        g2.setFont(font);
        LineMetrics lineMetrics = font.getLineMetrics(declareText, context);
        FontMetrics fontMetrics = FontDesignMetrics.getMetrics(font);
        float offset = (defineWidth - fontMetrics.stringWidth(declareText)) / 2;
        float y = (defineHeight + lineMetrics.getAscent() - lineMetrics.getDescent() - lineMetrics.getLeading()) / 2;
        g2.drawString(declareText, (int) offset, (int) y);

        Graphics2D graph = source.createGraphics();
        //开启文字抗锯齿
        graph.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        //添加image
        int width = textImage.getWidth(null);
        int height = textImage.getHeight(null);

        Image src = textImage;
        graph.drawImage(src, 0, QRCODE_SIZE - 8, width, height, Color.WHITE, null);
        graph.dispose();
    }

    /**
     * 生成二维码（有logo）
     *
     * @param jsonString 二维码内容  JSON字符串
     * @param logoPath   logo路径
     * @return Exception
     */
    public static MultipartFile createQrCode(String jsonString, String logoPath) throws Exception {

        return initMultipartFile(jsonString, logoPath);
    }

    /**
     * 生成二维码（无logo）
     *
     * @param jsonString 二维码内容  JSON字符串
     * @return
     */
    public static MultipartFile createQrCode(String jsonString) throws Exception {

        return initMultipartFile(jsonString, null);
    }

    public static MultipartFile createQrCode(String jsonString, String logoPath, String bottomText) throws Exception {
        return initMultipartFile(jsonString, logoPath, bottomText);
    }

    /**
     * 生成通行证二维码
     *
     * @param idCardNo   来访人身份证
     * @param userName   来访人姓名
     * @param userPhone  来访人手机号
     * @param plantNo    来访人车牌号
     * @param withNum    同行人数
     * @param ownerName  拜访目标人
     * @param ownerPhone 被访人手机号
     * @param roomId     被访房间id
     * @return
     * @throws Exception
     */
    public static MultipartFile createPassQrCode(String idCardNo, String userName, String userPhone, String plantNo, Integer withNum,
                                                 String ownerName, String ownerPhone, Long roomId) throws Exception {
        //二维码内容
        String content = "{" +
                "\"idCardNo\":\"" + idCardNo + "\"," +
                "\"userName\":\"" + userName + "\"," +
                "\"userPhone\":\"" + userPhone + "\"," +
                "\"plantNo\":\"" + plantNo + "\"," +
                "\"withNum\":\"" + withNum + "\"," +
                "\"ownerName\":\"" + ownerName + "\"," +
                "\"ownerPhone\":\"" + ownerPhone + "\"," +
                "\"roomId\":\"" + roomId + "\"" +
                "}";
        return initMultipartFile(content, null);
    }

    /**
     * 获取二维码图片
     *
     * @param dataStr    二维码内容
     * @param bottomText 底部文字       为空则不显示
     * @param formatName 图片后缀
     * @return
     */
    @SneakyThrows
    public static String getQRCodeImage(String dataStr, String bottomText, String formatName) {
        if (dataStr == null) {
            throw new RuntimeException("未包含任何信息");
        }
        HashMap<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");    //定义内容字符集的编码
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);        //定义纠错等级
        hints.put(EncodeHintType.MARGIN, 1);
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(dataStr, BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE, hints);

        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        int tempHeight = height;
        if (org.springframework.util.StringUtils.hasText(bottomText)) {
            tempHeight = tempHeight + 12;
        }
        BufferedImage image = new BufferedImage(width, tempHeight, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        // 判断是否添加底部文字
        if (org.springframework.util.StringUtils.hasText(bottomText)) {
            addFontImage(image, bottomText);
        }
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        try {
            ImageIO.write(image, formatName, stream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String base64 = "data:image/" + formatName + ";base64," + Base64.encode(stream.toByteArray());
        System.out.println(base64);
        return base64;
    }
}

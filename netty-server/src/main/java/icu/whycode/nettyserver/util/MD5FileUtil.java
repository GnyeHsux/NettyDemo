package icu.whycode.nettyserver.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author xps
 * @Description： 计算文件的 MD5值
 */
public class MD5FileUtil {
    static MessageDigest MD5 = null;

    static {
        try {
            MD5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ne) {
            ne.printStackTrace();
        }
    }

    private static final Logger logger = LoggerFactory.getLogger(MD5FileUtil.class);

    /**
     * 获取文件的 MD5
     *
     * @param file 文件
     * @return MD5 值
     * @throws IOException
     */
    public synchronized static String getFileMD5String(File file) throws IOException {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            byte[] buffer = new byte[8192];
            int length;
            while ((length = fileInputStream.read(buffer)) != -1) {
                MD5.update(buffer, 0, length);
            }
            return new String(Hex.encodeHex(MD5.digest()));
        } catch (IOException e) {
            logger.error("获取文件 md5 值异常！！！", e);
            return null;
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException e) {
                logger.error("close fileInputStream exception !!!", e);
            }
        }
    }


    public static void main(String[] args) throws IOException {
        long begin = System.currentTimeMillis();

        File big = new File("/Users/xps/Downloads/windows7.iso");
        String md5 = getFileMD5String(big);

        long end = System.currentTimeMillis();
        System.out.println("md5:" + md5);
        System.out.println("time:" + ((end - begin)));

    }
}

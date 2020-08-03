package icu.whycode.nettyclient.model;

import java.io.File;

/**
 * @author xps
 */
public class RequestFile {

    private File file;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 开始位置
     */
    private long starPos; 

    /**
     * 文件字节数组
     */
    private byte[] bytes; 

    /**
     * 结尾位置
     */
    private int endPos; 

    /**
     * 文件的MD5值
     */
    private String fileMd5;

    /**
     * 文件类型
     */
    private String fileType; 

    /**
     * 文件总长度
     */
    private long fileSize;

    public RequestFile() {
    }

    public long getStarPos() {
        return starPos;
    }

    public void setStarPos(long starPos) {
        this.starPos = starPos;
    }

    public int getEndPos() {
        return endPos;
    }

    public void setEndPos(int endPos) {
        this.endPos = endPos;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public String getFileMd5() {
        return fileMd5;
    }

    public void setFileMd5(String fileMd5) {
        this.fileMd5 = fileMd5;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}

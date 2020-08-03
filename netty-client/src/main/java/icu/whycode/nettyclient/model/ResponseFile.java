package icu.whycode.nettyclient.model;

import java.io.File;

public class ResponseFile {


    public ResponseFile() {

    }

    public ResponseFile(long start, String fileMd5, long progress) {
        super();
        this.start = start;
        this.fileMd5 = fileMd5;
        this.end = false;
        this.progress = (int) progress;
    }

    public ResponseFile(long start, String fileMd5, String file_url) {
        super();
        this.start = start;
        this.fileMd5 = fileMd5;
        this.fileUrl = file_url;
        this.end = true;
        this.progress = 100;
    }


    /**
     * 开始 读取点
     */
    private long start;
    /**
     * 文件的 MD5值
     */
    private String fileMd5;
    /**
     * 文件下载地址
     */
    private String fileUrl;
    /**
     * 上传是否结束
     */
    private boolean end;
    /**
     * 进度
     */
    private int progress;

    private File file; //文件

    private String fileName;// 文件名

    private String fileType;  //文件类型


    public long getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public String getFileMd5() {
        return fileMd5;
    }

    public void setFileMd5(String fileMd5) {
        this.fileMd5 = fileMd5;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public boolean isEnd() {
        return end;
    }

    public void setEnd(boolean end) {
        this.end = end;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
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

    @Override
    public String toString() {
        return "ResponseFile{" +
                "start=" + start +
                ", fileMd5='" + fileMd5 + '\'' +
                ", fileUrl='" + fileUrl + '\'' +
                ", end=" + end +
                ", progress=" + progress +
                ", fileName='" + fileName + '\'' +
                ", fileType='" + fileType + '\'' +
                '}';
    }
}

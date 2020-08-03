package icu.whycode.nettyserver;

import ch.qos.logback.core.util.FileUtil;
import icu.whycode.nettyserver.model.RequestFile;
import icu.whycode.nettyserver.model.ResponseFile;
import icu.whycode.nettyserver.util.FileTransferProperties;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.RandomAccessFile;

public class FileTransferServerHandler extends ChannelInboundHandlerAdapter {
    private static final Logger log = LoggerFactory.getLogger(FileTransferServerHandler.class);

    private volatile int byteRead;
    private volatile long start = 0;

    /**
     * 文件默认存储地址
     */
    private String file_dir = FileTransferProperties.getString("file_write_path", "/");

    private RandomAccessFile randomAccessFile;
    private File file;
    private long fileSize = -1;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof RequestFile) {
            RequestFile ef = (RequestFile) msg;
            log.info("接收文件中...{}", ef.getFileName());
            byte[] bytes = ef.getBytes();
            byteRead = ef.getEndPos();

            //文件MD5值
            String md5 = ef.getFileMd5();

            // 只有在文件开始传的时候才进入 这样就减少了对象创建 和可能出现的一些错误
            if (start == 0) {
                String path = file_dir + File.separator + md5 + ef.getFileType();
                FileUtil.createMissingParentDirectories(file);
                file = new File(path);
                fileSize = ef.getFileSize();

                //根据 MD5 和 文件类型 来确定是否存在这样的文件 如果存在就 秒传
                if (file.exists()) {
                    log.info("文件已存在:" + ef.getFileName() + "--" + ef.getFileMd5() + "[" + ctx.channel().remoteAddress() + "]");
                    ResponseFile responseFile = new ResponseFile(start, md5, "getFilePath()");
                    ctx.writeAndFlush(responseFile);

                    // TODO 这里可以做 断点续传 ，读取当前已经存在文件的总长度  和 传输过来的文件总长度对比 如果不一致，则认为本地文件没有传完毕 则续传
                    // 不过这步骤必须做好安全之后来做，否则可能会出现 文件被恶意加入内容
                    return;
                }

                randomAccessFile = new RandomAccessFile(file, "rw");
            }

            randomAccessFile.seek(start);
            randomAccessFile.write(bytes);
            start = start + byteRead;

            if (byteRead > 0 && (start < fileSize && fileSize != -1)) {
                log.info((start * 100) / fileSize + "::::" + fileSize + "::: " + (start * 100));
                ResponseFile responseFile = new ResponseFile(start, md5, (start * 100) / fileSize);
                ctx.writeAndFlush(responseFile);
            } else {
                log.info("接收文件：{} 完毕...", ef.getFileName());

                ResponseFile responseFile = new ResponseFile(start, md5, "getFilePath()");
                ctx.writeAndFlush(responseFile);

                randomAccessFile.close();
                file = null;
                fileSize = -1;
                randomAccessFile = null;
                //ctx.close();  这步让客户端来做
            }
        }
    }
}

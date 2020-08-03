package icu.whycode.nettyclient;

import icu.whycode.nettyclient.model.RequestFile;
import icu.whycode.nettyclient.model.ResponseFile;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class FileTransferClientHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(FileTransferClientHandler.class);

    private int byteRead;
    private volatile long start = 0;
    public RandomAccessFile randomAccessFile;
    private RequestFile request;
    private final int minReadBufferSize = 102400;

    private long startTime = 0L;
    private long endTime = 0L;

    public FileTransferClientHandler(RequestFile ef) {
        if (ef.getFile().exists()) {
            if (!ef.getFile().isFile()) {
                System.out.println("Not a file :" + ef.getFile());
                return;
            }
            this.request = ef;
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        try {
            randomAccessFile = new RandomAccessFile(request.getFile(), "r");
            randomAccessFile.seek(request.getStarPos());
            byte[] bytes = new byte[minReadBufferSize];
            //  =====>>>>>>读写文件
            logger.info("开始读取文件: {}", request.getFileName());
            startTime = System.currentTimeMillis();
            if ((byteRead = randomAccessFile.read(bytes)) != -1) {
                request.setEndPos(byteRead);
                request.setBytes(bytes);
                request.setFileSize(randomAccessFile.length());
                ctx.writeAndFlush(request);
            } else {
                logger.info("The file has been read！");
            }
        } catch (FileNotFoundException e) {
            logger.error("文件异常！！！", e);
        } catch (IOException io) {
            logger.error("IO 异常！！！", io);
        }
    }

    /**
     * channelRead 读取
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        if (msg instanceof ResponseFile) {
            ResponseFile response = (ResponseFile) msg;
            logger.info("---->>>> 上传进度: {} %", response.getProgress());

            if (response.isEnd()) {
                endTime = System.currentTimeMillis();
                logger.info("上传结束...上传耗时：{}秒", (endTime - startTime) / 1000);
                randomAccessFile.close();
                ctx.close();
            } else {
                start = response.getStart();
                if (start != -1) {
                    randomAccessFile = new RandomAccessFile(request.getFile(), "r");
                    randomAccessFile.seek(start);
                    int a = (int) (randomAccessFile.length() - start);
                    int sendLength = minReadBufferSize;
                    if (a < minReadBufferSize) {
                        sendLength = a;
                    }
                    byte[] bytes = new byte[sendLength];
                    if ((byteRead = randomAccessFile.read(bytes)) != -1 && (randomAccessFile.length() - start) > 0) {
                        request.setEndPos(byteRead);
                        request.setBytes(bytes);
                        try {
                            ctx.writeAndFlush(request);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        randomAccessFile.close();
                        ctx.close();
                    }
                }
            }
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        logger.error("异常！！！", cause);
        ctx.close();
    }
}

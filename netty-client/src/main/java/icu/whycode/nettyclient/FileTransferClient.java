package icu.whycode.nettyclient;

import icu.whycode.nettyclient.code.NettyMessageDecoder;
import icu.whycode.nettyclient.code.NettyMessageEncoder;
import icu.whycode.nettyclient.model.RequestFile;
import icu.whycode.nettyclient.util.MD5FileUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class FileTransferClient {

    private static final Logger logger = LoggerFactory.getLogger(FileTransferClient.class);

    public void connect(int port, String host, final RequestFile echoFile) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true).handler(new ChannelInitializer<Channel>() {

                @Override
                protected void initChannel(Channel ch) throws Exception {
                    ch.pipeline().addLast(new ObjectEncoder());
                    ch.pipeline().addLast(new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.weakCachingConcurrentResolver(null))); // 最大长度

                    ch.pipeline().addLast(new NettyMessageDecoder());//设置服务器端的编码和解码
                    ch.pipeline().addLast(new NettyMessageEncoder());
                    ch.pipeline().addLast(new FileTransferClientHandler(echoFile));
                }
            });
            ChannelFuture f = b.connect(host, port).sync();
            f.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }

    private static String getSuffix(String fileName) {
        String fileType = fileName.substring(fileName.lastIndexOf("."), fileName.length());
        return fileType;
    }

    public static void main(String[] args) {
        int port = 10012;
        try {
            RequestFile echo = new RequestFile();
//            File file = new File("/Users/huyh/Desktop/z_download/1002omdmain.db");
            File file = new File("/Users/huyh/Desktop/z_download/omdmain.zip");
            String fileName = file.getName();
            echo.setFile(file);
            echo.setFileMd5(MD5FileUtil.getFileMD5String(file));
            echo.setFileName(fileName);
            echo.setFileType(getSuffix(fileName));
            // 文件开始位置
            echo.setStarPos(0);
            new FileTransferClient().connect(port, "172.17.13.72", echo);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

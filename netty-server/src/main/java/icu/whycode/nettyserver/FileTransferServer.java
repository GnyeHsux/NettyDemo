package icu.whycode.nettyserver;

import icu.whycode.nettyserver.util.FileTransferProperties;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FileTransferServer {

    private static final Logger logger = LoggerFactory.getLogger(FileTransferServer.class);

    public void bind(int port) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(new FileChannelInitializer());

            logger.info("server bind port:" + port);

            ChannelFuture f = b.bind(port).sync();
            f.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    private static void init() {
        //请把加载属性文件放在 加载日志配置的上面，因为读取日志输出的目录配置在 属性文件里面
        FileTransferProperties.load("serverConfig.properties");

        System.setProperty("WORKDIR", FileTransferProperties.getString("WORKDIR", "/"));
    }

    public static void main(String[] args) {
        init();
        // 获取端口
        int port  = FileTransferProperties.getInt("port",10012);

        if (args != null && args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                logger.error(e.getMessage(), e);
            }
        }

        try {
            new FileTransferServer().bind(port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package icu.whycode.nettyserver;

import icu.whycode.nettyserver.code.NettyMessageDecoder;
import icu.whycode.nettyserver.code.NettyMessageEncoder;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

public class FileChannelInitializer extends ChannelInitializer<Channel> {

    @Override
    protected void initChannel(Channel channel) throws Exception {
        channel.pipeline().addLast(new ObjectEncoder());

        // 最大长度
        channel.pipeline().addLast(new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.weakCachingConcurrentResolver(null)));

        //设置服务器端的编码和解码
        channel.pipeline().addLast(new NettyMessageDecoder());
        channel.pipeline().addLast(new NettyMessageEncoder());

//        channel.pipeline().addLast(new SecureServerHandler());
        channel.pipeline().addLast(new FileTransferServerHandler());
    }
}

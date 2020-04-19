package com.cdz.netty.sixthexample;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.time.LocalDateTime;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: CDz
 * Create: 2020-04-16 13:38
 **/
public class TestClientHandler extends SimpleChannelInboundHandler<MyDataInfo.Person> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.Person msg) throws Exception {
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

//    @Override
//    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
//        ctx.writeAndFlush("from client first message");
////    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        MyDataInfo.Person person = MyDataInfo.Person.newBuilder()
                .setName("张三")
                .setAddress("地址")
                .setAge(18).build();
        ctx.writeAndFlush(person);
    }
}

package com.cdz.netty.proto.demo;

import com.cdz.netty.proto.MyRequest;
import com.google.protobuf.InvalidProtocolBufferException;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: CDz
 * Date: 2020-05-22
 * Time: 11:05
 *
 * format test
 */
public class ProtoFormatTest {

    public static void main(String[] args) throws InvalidProtocolBufferException {
        MyRequest request = MyRequest.newBuilder().setUsername("张三").build();

        byte[] bytes = request.toByteArray();

        MyRequest myRequest = MyRequest.parseFrom(bytes);


        System.out.println(myRequest.getUsername());
        System.out.println(myRequest==request);
    }
}

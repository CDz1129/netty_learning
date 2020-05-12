package com.cdz.netty.proto.demo;

import com.cdz.netty.proto.MyRequest;
import com.cdz.netty.proto.MyResponse;
import com.cdz.netty.proto.StudentServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: CDz
 * Create: 2020-05-12 22:35
 **/
public class StudentClient {



    public static void main(String[] args) {

        ManagedChannel channel = ManagedChannelBuilder.forTarget("localhost:50051")
                .usePlaintext()
                .build();
        MyRequest myRequest = MyRequest.newBuilder().setUsername("cdz").build();


        final StudentServiceGrpc.StudentServiceBlockingStub blockingStub = StudentServiceGrpc.newBlockingStub(channel);

        MyResponse realName = blockingStub.getRealName(myRequest);
        System.out.println("gRPC返回："+realName.getRealname());
    }
}

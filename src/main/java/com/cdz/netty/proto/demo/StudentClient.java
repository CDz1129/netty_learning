package com.cdz.netty.proto.demo;

import com.cdz.netty.proto.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.Iterator;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

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


        final StudentServiceGrpc.StudentServiceBlockingStub blockingStub = StudentServiceGrpc.newBlockingStub(channel);

        MyRequest myRequest = MyRequest.newBuilder().setUsername("cdz").build();
        MyResponse realName = blockingStub.getRealName(myRequest);
        System.out.println("-----------------simple --------------------");
        System.out.println("gRPC返回："+realName.getRealname());
        System.out.println("-----------------simple --------------------");
//
        System.out.println("--------response stream-----------------");
        StudentRequest studentRequest = StudentRequest.newBuilder().setAge(20).build();
        Iterator<StudentResponse> studentListByAge = blockingStub.getStudentListByAge(studentRequest);

        while (studentListByAge.hasNext()){
            System.out.println("---------------");
            StudentResponse studentResponse = studentListByAge.next();

            System.out.println(studentResponse.getName());
            System.out.println(studentResponse.getAddress());
            System.out.println(studentResponse.getAge());
            System.out.println("---------------");
        }
        System.out.println("--------response stream-----------------");

        System.out.println("---------request stream-----------------------");

        final StudentServiceGrpc.StudentServiceStub asynStub = StudentServiceGrpc.newStub(channel);

        StreamObserver<StudentRequest> requestStreamObserver = asynStub.getStudentByAgeList(new StreamObserver<StudentResponse>() {
            @Override
            public void onNext(StudentResponse studentResponse) {
                System.out.println("---------------");
                System.out.println(studentResponse.getName());
                System.out.println(studentResponse.getAddress());
                System.out.println(studentResponse.getAge());
                System.out.println("---------------");

            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onCompleted() {

                System.out.println("发送完毕");
            }
        });

        requestStreamObserver.onNext(StudentRequest.newBuilder().setAge(10).build());
        requestStreamObserver.onNext(StudentRequest.newBuilder().setAge(20).build());
        requestStreamObserver.onNext(StudentRequest.newBuilder().setAge(30).build());
        requestStreamObserver.onNext(StudentRequest.newBuilder().setAge(40).build());
        requestStreamObserver.onCompleted();
        System.out.println("---------request stream-----------------------");

        System.out.println("---------request and response stream-----------------------");


        StreamObserver<StudentRequest> studentListByAgeList = asynStub.getStudentListByAgeList(new StreamObserver<StudentResponse>() {
            @Override
            public void onNext(StudentResponse value) {
                System.out.println(value.getAddress());
            }

            @Override
            public void onError(Throwable t) {
                t.printStackTrace();
            }


            @Override
            public void onCompleted() {

                System.out.println("完成");
            }
        });

        for (int i = 0; i < 100; i++) {
            studentListByAgeList.onNext(StudentRequest.newBuilder().setAge((int) System.currentTimeMillis()/1000).build());

        }


        studentListByAgeList.onCompleted();

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

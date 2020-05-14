package com.cdz.netty.proto.demo;

import com.cdz.netty.proto.*;
import io.grpc.stub.StreamObserver;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: CDz
 * Create: 2020-05-14 21:21
 **/
public class StudentServiceImpl extends StudentServiceGrpc.StudentServiceImplBase {

    @Override
    public void getRealName(MyRequest request, StreamObserver<MyResponse> responseObserver) {

        MyResponse response;
        if (request.getUsername().equals("CDz")) {
            response = MyResponse.newBuilder().setRealname("陈德智").build();
        } else {
            response = MyResponse.newBuilder().setRealname("张三").build();
        }

        responseObserver.onNext(response);
        responseObserver.onCompleted();
//            super.getRealName(request, responseObserver);
    }

    @Override
    public void getStudentListByAge(StudentRequest request, StreamObserver<StudentResponse> responseObserver) {
        System.out.println("收到request："+request.getAge());
        responseObserver.onNext(StudentResponse.newBuilder()
                .setName("张三")
                .setAddress("深圳")
                .setAge(18).build());
        responseObserver.onNext(StudentResponse.newBuilder()
                .setName("李四")
                .setAddress("北京")
                .setAge(19).build());
        responseObserver.onNext(StudentResponse.newBuilder()
                .setName("王五")
                .setAddress("上海")
                .setAge(20).build());
        responseObserver.onNext(StudentResponse.newBuilder()
                .setName("赵六")
                .setAddress("成都")
                .setAge(21).build());

        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<StudentRequest> getStudentByAgeList(StreamObserver<StudentResponse> responseObserver) {



        return new StreamObserver<StudentRequest>() {
            @Override
            public void onNext(StudentRequest value) {
                System.out.println("onNext:"+value.getAge());
            }

            @Override
            public void onError(Throwable t) {

                responseObserver.onError(new RuntimeException("出错"));
            }

            @Override
            public void onCompleted() {

                responseObserver.onNext(StudentResponse.newBuilder()
                        .setName("张三")
                        .setAddress("地址")
                        .setAge(10).build());
                responseObserver.onCompleted();
            }
        };
    }

    @Override
    public StreamObserver<StudentRequest> getStudentListByAgeList(StreamObserver<StudentResponse> responseObserver) {
        return new StreamObserver<StudentRequest>() {
            @Override
            public void onNext(StudentRequest value) {
                responseObserver.onNext(StudentResponse.newBuilder()
                        .setName("张三"+value.getAge())
                        .setAddress("地址"+value.getAge())
                        .setAge(value.getAge()).build());
            }

            @Override
            public void onError(Throwable t) {

                responseObserver.onError(new RuntimeException("出错"));
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }
}
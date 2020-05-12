package com.cdz.netty.protobuf;

import com.google.protobuf.InvalidProtocolBufferException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DataInfoTest {


    @Test
    void testBuild() {
        DataInfo.Student student = DataInfo.Student.newBuilder()
                .setName("张三")
                .setAge(18)
                .setAddress("深圳").build();
        assertEquals(student.getName(),"张三");
        assertEquals(student.getAddress(),"深圳");
        assertEquals(student.getAge(),18);
    }


    @Test
    void testTransform() throws InvalidProtocolBufferException {

        DataInfo.Student student = DataInfo.Student.newBuilder()
                .setName("张三")
                .setAge(19)
                .setAddress("深圳")
                .build();
        byte[] byteArray = student.toByteArray();


        DataInfo.Student student1 = DataInfo.Student.parseFrom(byteArray);

        assertEquals(student,student1);

    }

    @Test
    void testDataInfo() {
    }
}
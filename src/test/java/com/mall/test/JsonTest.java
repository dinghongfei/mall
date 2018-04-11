package com.mall.test;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import java.io.IOException;

public class JsonTest {

    @Test
    public void test1(){
        Student stu = new Student("lisi", 19, "heheda");
        ObjectMapper mapper = new ObjectMapper();
        try {
            System.out.println(mapper.writeValueAsString(stu));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}

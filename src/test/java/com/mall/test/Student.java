package com.mall.test;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize
public class Student {

    private String name;
    private int age;
    private String msg;

    public Student(String name, int age, String msg) {
        this.name = name;
        this.age = age;
        this.msg = msg;
    }

    public boolean isSuccess(){
        return true;
    }

    @JsonIgnore
    public boolean isHheheh(){
        return false;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

package com.mall.util;

import com.google.common.collect.Lists;
import com.mall.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.type.JavaType;
import org.codehaus.jackson.type.TypeReference;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 *
 *@author dhf
 */
@Slf4j
public class JsonUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        //对象的所有字段全部列入
        objectMapper.setSerializationInclusion(Inclusion.ALWAYS);

        //取消默认转换timestamps形式
        objectMapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);

        //所有的日期格式都统一为以下的样式：yyyy-MM-dd HH:mm:ss
        objectMapper.setDateFormat(new SimpleDateFormat(DateTimeUtil.STANDARD_FORMAT));

        //忽略空bean转json的错误
        objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);


        //忽略在json字符串中存在，但是在java对象中不存在对应属性的情况，防止错误,反序列化时会用到
        objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    }

    public static <T> String obj2String(T obj){
        if (obj == null) {
            return null;
        }

        try {
            return obj instanceof String ? (String)obj : objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            log.warn("Parse object to String error",e);
            return null;
        }
    }


    public static <T> String obj2StringPretty(T obj){
        if (obj == null) {
            return null;
        }
        try {
            return obj instanceof String ? (String)obj : objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (Exception e) {
            log.warn("Parse object to String error",e);
            return null;
        }
    }

    public static <T> T string2Obj(String str,Class<T> clazz){
        if (StringUtils.isEmpty(str) || clazz == null) {
            return null;
        }

        try {
            return clazz.equals(String.class)?(T)str : objectMapper.readValue(str, clazz);
        } catch (Exception e) {
            log.warn("Parse String to Object error",e);
            return null;
        }
    }

    public static <T> T string2Obj(String str, TypeReference typeReference){
        if (StringUtils.isEmpty(str) || typeReference == null) {
            return null;
        }

        try {
            return (T)(typeReference.getType().equals(String.class)? str:objectMapper.readValue(str,typeReference));
        } catch (Exception e) {
            log.warn("Parse String to Object error",e);
            return null;
        }
    }


    public static <T> T string2Obj(String str, Class<?> collectiongClass,Class<?>... elementClasses){
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(collectiongClass, elementClasses);
        try {
            return objectMapper.readValue(str,javaType);
        } catch (Exception e) {
            log.warn("Parse String to Object error",e);
            return null;
        }
    }









    public static void main(String[] args) {
        User u1 = new User();
        u1.setId(1);
        u1.setEmail("adg1@q63.com");

        String obj2String = JsonUtil.obj2String(u1);
        String obj2StringPretty = JsonUtil.obj2StringPretty(u1);

        log.info("user:{}",obj2String);
        log.info("user:{}",obj2StringPretty);

        User user = JsonUtil.string2Obj(obj2String, User.class);

        User u2 = new User();
        u2.setId(2);
        u2.setEmail("eeeeee1@q63.com");

        List<User> userList = Lists.newArrayList();
        userList.add(u1);
        userList.add(u2);

        String userListString = JsonUtil.obj2StringPretty(userList);

        log.info("+++++++++++");
        log.info("userList:{}",userListString);


        List<User> list = JsonUtil.string2Obj(userListString, List.class);

        List<User> listUser = JsonUtil.string2Obj(userListString, new TypeReference<List<User>>() {
        });


        List<User> userList2= JsonUtil.string2Obj(userListString, List.class,User.class);



//        没弄懂多参数是怎么使用
//        List userList3 = Lists.newArrayList();
//        userList3.add(u1);
//        userList3.add(11);
//        userList3.add(true);
//
//        String sAll = JsonUtil.obj2StringPretty(userList3);
//
//        List sList = JsonUtil.string2Obj(sAll, List.class,User.class, Integer.class, Boolean.class);

        System.out.println("end");
    }




















}




package com.mall.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * properties文件处理工具类
 * @author dhf
 */
@Slf4j
public class PropertiesUtil {

    private static Properties props;

    static {
        String fileName = "mmall.properties";
        props = new Properties();
        try {
            props.load(new InputStreamReader(PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName),"UTF-8"));
        } catch (IOException e) {
            log.error("配置文件读取异常",e);
        }
    }

    public static String getPropertry(String key){
        String value = props.getProperty(key.trim());
        if (StringUtils.isBlank(value)) {
            return null;
        }
        return value.trim();
    }

    public static String getPropertry(String key,String defaultValue){
        String value = props.getProperty(key.trim());
        if (StringUtils.isBlank(value)) {
            value = defaultValue;
        }
        return value.trim();
    }

    public static Integer getIntegerPropertry(String key){
        String value = props.getProperty(key.trim());
        if (StringUtils.isBlank(value)) {
            return null;
        }
        return Integer.valueOf(value.trim());
    }
    public static Integer getIntegerPropertry(String key,Integer defaultValue){
        String value = props.getProperty(key.trim());
        if (StringUtils.isBlank(value)) {
            return defaultValue;
        }
        return Integer.valueOf(value.trim());
    }

    public static Boolean getBooleanPropertry(String key){
        String value = props.getProperty(key.trim());
        if (StringUtils.isBlank(value)) {
            return null;
        }
        return Boolean.valueOf(value.trim());
    }
    public static Boolean getBooleanPropertry(String key,Boolean defaultValue){
        String value = props.getProperty(key.trim());
        if (StringUtils.isBlank(value)) {
            return defaultValue;
        }
        return Boolean.valueOf(value.trim());
    }





}

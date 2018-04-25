package com.mall.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * cookie工具类
 *@author dhf
 */
@Slf4j
public class CookieUtil {

    private final static String COOKIE_DOMAIN = ".dhf.com";
    private final static String COOKIE_NAME = "mall_login_token";


    /**
     * 读取请求中的登录cookie
     * @param request   request
     * @return          String
     */
    public static String readLoginToken(HttpServletRequest request){
        Cookie[] cks = request.getCookies();
        if (cks != null) {
            for (Cookie ck : cks) {
                log.info("cookieName:{},cookieValue:{}",ck.getName(),ck.getValue());
                if (StringUtils.equals(ck.getName(),COOKIE_NAME)){
                    log.info("return cookieName:{},cookieValue:{}",ck.getName(),ck.getValue());
                    return ck.getValue();
                }
            }
        }
        return null;
    }


    /**
     * 写一个登录cookie，加入到response，controller中会返回给前端
     * @param response  response
     * @param token     token
     */
    public static void writeLoginToken(HttpServletResponse response,String token){
        Cookie ck = new Cookie(COOKIE_NAME, token);
        ck.setDomain(COOKIE_DOMAIN);
        ck.setPath("/");
        ck.setHttpOnly(true);
        //单位s
        //木设置的话，cookie不会写入硬盘，而是写在内存，只在当前页面有效
        ck.setMaxAge(60 * 60 * 24 * 365);//-1 永久
        log.info("write cookieName:{},cookieVlue:{}",ck.getName(),ck.getValue());

        response.addCookie(ck);
    }

    /**
     * 删除登录cookie，这里是设置cookie时间为0
     * @param request   request
     * @param response  response
     */
    public static void delLoginToken(HttpServletRequest request,HttpServletResponse response){
        Cookie[] cks = request.getCookies();
        if (cks != null) {
            for (Cookie ck : cks) {
                if (StringUtils.equals(ck.getName(),COOKIE_NAME)){
                    ck.setDomain(COOKIE_DOMAIN);
                    ck.setPath("/");
                    ck.setMaxAge(0);//删除cookie
                    log.info("delete cookieName:{},cookieValue:{}",ck.getName(),ck.getValue());
                    response.addCookie(ck);
                    return;
                }
            }
        }
    }





}

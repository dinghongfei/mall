package com.mall.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局异常处理
 * 这里注意@Component的使用，注册成bean后才能生效
 * @author dhf
 */
@Slf4j
@Component
public class ExceptionResolver implements HandlerExceptionResolver{

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        log.error("{} Exception",httpServletRequest.getRequestURI(),e);
        //jackson2.x时使用MappingJackson2JsonView
        ModelAndView modelAndView = new ModelAndView(new MappingJacksonJsonView());

        //这里异常信息包装成和ServerResponse公用返回类一致的结构。
        modelAndView.addObject("status", ResponseCode.ERROR.getCode());
        modelAndView.addObject("msg", "接口异常,请查看服务端日志");
        modelAndView.addObject("data", e.toString());

        return modelAndView;
    }
}

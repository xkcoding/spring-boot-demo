package com.xkcoding.component.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 过滤器  定义filterName和过滤的url
@WebFilter(filterName = "myFilter" ,urlPatterns = "/staff/info/*")
public class CommonFilterAnnotation implements Filter {
    /**
     * filter对象只会创建一次，init方法也只会执行一次。
     * @param filterConfig
     * @throws ServletException
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("CommonFilterAnnotation初始化完成....");
    }
    /**
     * 主要的业务代码编写方法
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        System.out.println("啦啦啦");
        //获取session中保存的对象
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        //获取请求路径
        String servletPath = req.getServletPath();
        System.out.println( "servletPath = " + servletPath );

        if (servletPath.equals( "/staff/info/upload" )){
            String path = "/staff/info/fq";
            //改变前端请求的后端路径为path
            req.getRequestDispatcher(path).forward(req,resp);
            //放行
           // chain.doFilter(req,resp);
           // return;

        }
        //放行请求
        else {
            chain.doFilter(request,response);
        }

    }
    /**
     * 在销毁Filter时自动调用。
     */
    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}

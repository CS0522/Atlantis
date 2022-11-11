package com.atlantis.filter;

import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 设置过滤后台和用户请求路径
//@Component
@WebFilter(filterName = "loginCheckFilter", urlPatterns = {"/admins/*", "/users/*"})
public class LoginCheckFilter implements Filter {
    // 路径匹配器，支持通配符
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    public boolean checkIsInExcludeUrls(String[] urls, String requestURI)
    {
        for (String url : urls)
        {
            if (PATH_MATCHER.match(url, requestURI))
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        // transform
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        /*
        1. 获取本次请求的uri
        2. 判断本次请求是否需要处理
        3. 不需要处理，直接放行
        4. 如果已登录，直接放行
        5. 如果未登录，返回未登录结果
         */
        System.out.println("检测到请求：" + request.getRequestURI());

        // 1
        String requestURI = request.getRequestURI();
        // 2
        // 2.1 设置不需要处理的url
        String[] excludeUrls = new String[] {
                "/admins/login", "/admins/logout", "/users/login", "users/logout"
        };
        // 2.2
        boolean isInExcludeUrls = checkIsInExcludeUrls(excludeUrls, requestURI);
        // 3
        if (isInExcludeUrls)
        {
            filterChain.doFilter(request, response);
            return;
        }
        // 4
        System.out.println("value: " + request.getSession().getAttribute("admin"));
        System.out.println("session Id: " + request.getSession().getId());
        if (request.getSession().getAttribute("admin") != null ||
                request.getSession().getAttribute("user") != null)
        {


            filterChain.doFilter(request, response);
            return;
        }
        // 5
        System.out.println("已拦截请求：" + requestURI);
    }
}

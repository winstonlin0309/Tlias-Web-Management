package com.project.filter;

import com.project.utils.CurrentHolder;
import com.project.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@WebFilter(urlPatterns = "/*")
public class TokenFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //1. 获取到请求的路径
        String requestURI = request.getRequestURI();

        //2. 判断是否是登入请求, 如果路径包含 /login, 说明是登录操作, 放行
        if(requestURI.contains("/login")) {
            log.info("登入请求, 放行");
            //放行
            filterChain.doFilter(request, response);
            return;
        }

        //3. 获取请求头中的token
        String token = request.getHeader("token");

        //4. 判断token是否存在, 如果不存在, 说明用户没有登入, 返回错误(401)
        if(token == null || token.isEmpty()) {
            log.info("令牌为空, 响应401");
            response.setStatus(401);
            return;
        }

        //5. 如果token存在, 校验token, 校验失败则返回 401
        try {
            //这里如果有人篡改了 token的头或者payload, 那么他就会解析失败, 直接报错, 就会被这里的try-catch到
            Claims claims = JwtUtils.parseToken(token);
            Integer empId = Integer.valueOf(claims.get("id").toString());
            CurrentHolder.setCurrentId(empId);
            log.info("当前登入员工ID: {}, 将其存入ThreadLocal", empId);
        } catch (Exception e) {
            log.info("令牌非法, 响应401");
            response.setStatus(401);
        }

        //6. 校验通过 放行
        log.info("令牌合法, 放行");
        filterChain.doFilter(request, response);

        //7. 放行之后, 删除ThreadLocal中的数据
        CurrentHolder.remove();
    }
}

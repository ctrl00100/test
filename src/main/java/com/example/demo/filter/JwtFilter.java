package com.example.demo.filter;

import com.example.demo.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@Component
//public class JwtFilter extends HandlerInterceptorAdapter {
//    @Autowired
//    private JwtUtil jwtUtil;
//    @Override
//    public boolean preHandle(HttpServletRequest request,
//                             HttpServletResponse response, Object handler) throws Exception {
//        System.out.println("经过了拦截器");
//        return true;
//    }
//}
@Component
public class JwtFilter extends HandlerInterceptorAdapter {
    @Autowired
    private JwtUtil jwtUtil;
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        System.out.println("经过了拦截器");
        final String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            final String token = authHeader.substring(7); // The partafter "Bearer "
            try {
                Claims claims = jwtUtil.parseJWT(token);
                if (claims != null) {
                    if ("admin".equals(claims.get("roles"))) {//如果是管理员
                        request.setAttribute("admin_claims", claims);
                    }
                    if ("user".equals(claims.get("roles"))) {//如果是用户
                        request.setAttribute("user_claims", claims);
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException("令牌错误");
            }
        }
        return true;
    }
}

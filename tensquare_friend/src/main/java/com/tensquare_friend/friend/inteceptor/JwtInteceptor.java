package com.tensquare_friend.friend.inteceptor;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.HandlerInterceptor;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Repository
public class JwtInteceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        System.out.println("经过了拦截器");
        String head = request.getHeader("Authorization");
        if(head != null && !"".equals(head)){
            //如果请求头中包含Authorization的头信息，才对其操作
            if(head.startsWith("Bearer ")){
                //获取token
                String token = head.substring(7);
                //对令牌进行解析
                try{
                    Claims claims = jwtUtil.parseJWT(token);
                    String roles = (String) claims.get("roles");
                    if(roles!=null &&roles.equals("user")){
                        request.setAttribute("claim_user",claims);
                    }
                    if(roles !=null &&roles.equals("admin")){
                        request.setAttribute("claim_admin",claims);
                    }

                }catch (Exception e){
                    throw  new RuntimeException("令牌不正确");
                }

            }
        }

        return true;
    }
}

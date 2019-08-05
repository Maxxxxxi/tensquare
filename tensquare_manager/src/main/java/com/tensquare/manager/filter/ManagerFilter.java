package com.tensquare.manager.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;

@Component
public class ManagerFilter extends ZuulFilter {

    @Autowired
    private JwtUtil jwtUtil;

    //在进入网关之前执行
    @Override
    public String filterType() {
        return "pre";
    }

    //执行过滤器的等级，0表示最高
    @Override
    public int filterOrder() {
        return 0;
    }

    //是否开启次过滤器
    @Override
    public boolean shouldFilter() {
        return true;
    }

    //真正执行的逻辑内容
    @Override
    public Object run() throws ZuulException {
        System.out.println("经过了manager zuul的过滤器");

        RequestContext currentContext = RequestContext.getCurrentContext();

        HttpServletRequest request = currentContext.getRequest();

        //网关包含两次请求，第一次请求不携带头信息，直接放行
        if(request.getMethod() == "OPTIONS"){
            return null;
        }
        //如果是登录请求，也是放行。
        if(request.getRequestURL().indexOf("login")>0){
            return null;
        }

        String header = request.getHeader("Authorization");
        System.out.println(header);
        if(header!=null && !"".equals(header)){
            if(header.startsWith("Bearer ")){
                String token = header.substring(7);
                try {
                    System.out.println("token"+token);
                    Claims claims = jwtUtil.parseJWT(token);
                    String roles = (String) claims.get("roles");
                    System.out.println(roles);
                    if(roles.equals("admin")){
                        //把头信息传递下去，并且放行
                        currentContext.addZuulRequestHeader("Authorization",header);
                        return null;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    currentContext.setSendZuulResponse(false);
                }
            }
        }
        currentContext.setSendZuulResponse(false);
        currentContext.setResponseStatusCode(403);
        currentContext.getResponse().setContentType("text/html;charset=utf-8");
        currentContext.setResponseBody("权限不足");

        return null;
    }
}

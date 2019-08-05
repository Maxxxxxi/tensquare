package com.tensquare.web.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;


@Component
public class WebFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        //在这里做有信息的转发：得到头信息，然后进行转发到zuul
        //1.得到request的上下文
        RequestContext currentContext = RequestContext.getCurrentContext();
        //2.得到request域
        HttpServletRequest request = currentContext.getRequest();
        //3.得到头信息
        String header = request.getHeader("Authorization");
        //4.将头信息继续往下传
        if(header != null && !"".equals(header))
            currentContext.addZuulRequestHeader("Authorization",header);
        return null;
    }
}

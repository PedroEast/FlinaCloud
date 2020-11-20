package me.dongqinglin.zuul_gateway.AFilter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import me.dongqinglin.zuul_gateway.CServiceImpl.MyUserDetailService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 2020
 *
 */
public class UserTokenFilter extends ZuulFilter {

    @Autowired
    MyUserDetailService userDetailService;

    private final Logger LOGGER = LoggerFactory.getLogger(UserTokenFilter.class);

    // private String[] filterTypes = {"pre", "routing", "ost", "error"};
    // PRE：这种过滤器在请求被路由之前调用。我们可利用这种过滤器实现身份验证、在集群中选择请求的微服务、记录调试信息等。
    // ROUTING：这种过滤器将请求路由到微服务。这种过滤器用于构建发送给微服务的请求，并使用Apache HttpClient或Netfilx Ribbon请求微服务。
    // OST：这种过滤器在路由到微服务以后执行。这种过滤器可用来为响应添加标准的HTTP Header、收集统计信息和指标、将响应从微服务发送给客户端等。
    // ERROR：在其他阶段发生错误时执行该过滤器。
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        HttpServletResponse response = ctx.getResponse();
        LOGGER.info("--->>> UserTokenFilter {},{}", request.getMethod(), request.getRequestURL().toString());
        if(response.getHeader("isAuthSuccess") == "true"){
            ctx.addZuulRequestHeader("is-auth-success", "true");
            ctx.setSendZuulResponse(true); //对请求进行路由
            ctx.setResponseStatusCode(200);
            System.out.println("转载路由");
        }else {
            System.out.println("不会转载路由");
            ctx.setSendZuulResponse(false); //对请求进行路由
            ctx.setResponseStatusCode(403);
        }
        // System.out.println("走这里");
        return null;
    }
}

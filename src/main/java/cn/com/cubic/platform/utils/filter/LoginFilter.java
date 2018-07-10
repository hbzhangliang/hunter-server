package cn.com.cubic.platform.utils.filter;

import cn.com.cubic.platform.utils.CookieUtils;
import cn.com.cubic.platform.utils.sso.SSOConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Liang.Zhang on 2018/7/6.
 **/
@Component
public class LoginFilter implements Filter{
    private static final Logger log = LoggerFactory.getLogger(LoginFilter.class);
    private SSOConfig ssoConfig;
    private String[] excludePaths;
    //未加密的
    private static final String TOKEN_PARAM_NAME="token";
    //加密后的
    private static final String ENCODE_TOKEN_PARAM_NAME="encode_token_cookie";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        ServletContext servletContext = filterConfig.getServletContext();
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        this.ssoConfig = (SSOConfig)ctx.getBean(SSOConfig.class);
        String excludePath = this.ssoConfig.getExcludePaths();
        if (StringUtils.isEmpty(excludePath)) {
            this.excludePaths = new String[0];
        } else {
            this.excludePaths = excludePath.split(",");
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        if (this.ssoConfig == null) {
            throw new IOException("sso config bean must set");
        } else {
            String encodeToken= CookieUtils.getCookie(request,ENCODE_TOKEN_PARAM_NAME);
            if(StringUtils.isEmpty(encodeToken)){
                log.warn("未查询到cookie数据，直接导航到登录页面");
                response.sendRedirect(ssoConfig.getLoginUrl());
                return;
            }
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}

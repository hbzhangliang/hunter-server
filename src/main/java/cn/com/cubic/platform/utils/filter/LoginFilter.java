package cn.com.cubic.platform.utils.filter;

import cn.com.cubic.platform.utils.CodeUtils;
import cn.com.cubic.platform.utils.CookieUtils;
import cn.com.cubic.platform.utils.RedisUtils;
import cn.com.cubic.platform.utils.sso.SSOConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
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
    private AntPathMatcher antPathMatcher;
    //未加密的
    private static final String TOKEN_PARAM_NAME="token";
    //加密后的
    private static final String ENCODE_TOKEN_PARAM_NAME="encode_token_cookie";

    private RedisUtils redisUtils;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        ServletContext servletContext = filterConfig.getServletContext();
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        this.ssoConfig = (SSOConfig)ctx.getBean(SSOConfig.class);
        this.redisUtils=ctx.getBean(RedisUtils.class);
        String excludePath = this.ssoConfig.getExcludePaths();
        this.antPathMatcher = new AntPathMatcher();
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
            String requestURI = request.getRequestURI().replace(request.getContextPath(), "");
            if(checkExclude(requestURI)){
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
            if(!StringUtils.isEmpty(encodeToken)){
                String token= CodeUtils.getDecodedToken(encodeToken);
                if(!StringUtils.isEmpty(token)&&redisUtils.getObj(token)!=null){
                    filterChain.doFilter(servletRequest, servletResponse);
                    return;
                }
            }
            response.sendRedirect(ssoConfig.getLoginUrl());
            return;
        }
    }


    /**
     * 判断排除在外
     * @param requestURI
     * @return
     */
    private boolean checkExclude(String requestURI) {
        if (this.ssoConfig !=null) {
            String[] var2 = this.excludePaths;
            int var3 = var2.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                String path = var2[var4];
                if (this.antPathMatcher.match(path, requestURI)) {
                    return true;
                }
            }

            return false;
        }
        return false;
    }


    @Override
    public void destroy() {

    }
}

package cn.com.cubic.platform.hunter.WebSocketTs;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by Liang.Zhang on 2018/7/4.
 **/
@WebListener
public class RequestListener implements ServletRequestListener {


    @Override
    public void requestDestroyed(ServletRequestEvent servletRequestEvent) {

    }

    @Override
    public void requestInitialized(ServletRequestEvent servletRequestEvent) {
        ((HttpServletRequest)servletRequestEvent.getServletRequest()).getSession();
    }
}

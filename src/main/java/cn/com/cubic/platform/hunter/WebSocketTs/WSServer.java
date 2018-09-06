package cn.com.cubic.platform.hunter.WebSocketTs;

import cn.com.cubic.platform.hunter.controller.BaseController;
import cn.com.cubic.platform.hunter.mysql.entity.TSysAccount;
import cn.com.cubic.platform.utils.RedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.socket.server.standard.SpringConfigurator;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.swing.*;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by Liang.Zhang on 2018/7/4.
 **/
@Controller
@ServerEndpoint(value = "/chat",configurator = HttpSessionConfigurator.class)
public class WSServer {

    private static final Logger log = LoggerFactory.getLogger(WSServer.class);

    private static int onlineCount=0;

    static public final ConcurrentMap<String,WSServer> map=new ConcurrentHashMap<>(10);

    private Session session;

    private HttpSession httpSession;

    private RedisUtils redisUtils;

    @OnOpen
    public void onOpen(Session session, EndpointConfig config){
        if(this.redisUtils==null) {
            this.redisUtils = ContextLoader.getCurrentWebApplicationContext().getBean(RedisUtils.class);
        }
        this.httpSession=(HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        this.session=session;
        log.info("sessionId is [{}]",this.session.getId());

        TSysAccount account=(TSysAccount) redisUtils.getObj(httpSession.getId());
        if(map.get(account.getId().toString())==null){
            map.put(account.getId().toString(),this);
            addOnlineCount();
        }
        log.info("有新的连接，当前连接数未[{}]",getOnlineCount());
    }

    @OnClose
    public void onClose(){
        TSysAccount account=(TSysAccount) redisUtils.getObj(httpSession.getId());
        if(map.get(account.getId().toString())!=null){
            map.remove(account.getId().toString());
            subOnlineCount();
        }
        log.info("有一个连接断开，当前连接数未[{}]",getOnlineCount());
    }


    @OnMessage
    public void onMessage(String message) throws IOException{
        TSysAccount account=(TSysAccount) redisUtils.getObj(httpSession.getId());
        WSServer _client=map.get(account.getId().toString());
        log.info("后端发送数据开始");
        if(null!=_client){
            while (_client.session.isOpen()){
                try {
                    Thread.sleep(5000);
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                _client.session.getBasicRemote().sendText("后端来的数据啦");
            }
        }
        log.info("后端发送数据结束");
    }

    @OnError
    public void onError(Throwable t){
        t.printStackTrace();
    }

//
//    static
//    public void pushBySys(){
//        WSServer ws=map.get("zs");
//        try {
//            if(ws!=null){
//                ws.session.getBasicRemote().sendText("123456");
//            }
//        } catch (IOException e1) {
//            e1.printStackTrace();
//        }
//
//    }


    // 获取连接数
    private static synchronized int getOnlineCount() {
        return WSServer.onlineCount;
    }

    // 增加连接数
    private static synchronized void addOnlineCount() {
        WSServer.onlineCount++;
    }

    // 减少连接数
    private static synchronized void subOnlineCount() {
        WSServer.onlineCount--;
    }




}

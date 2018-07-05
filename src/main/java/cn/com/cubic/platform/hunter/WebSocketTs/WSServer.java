package cn.com.cubic.platform.hunter.WebSocketTs;

import cn.com.cubic.platform.hunter.controller.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by Liang.Zhang on 2018/7/4.
 **/
@ServerEndpoint(value = "/chat",configurator = HttpSessionConfigurator.class)
public class WSServer {

    private static final Logger log = LoggerFactory.getLogger(WSServer.class);

    private static int onlineCount=0;

    static public final ConcurrentMap<String,WSServer> map=new ConcurrentHashMap<>(10);

    private Session session;

    private HttpSession httpSession;


    @OnOpen
    public void onOpen(Session session, EndpointConfig config){

        this.httpSession=(HttpSession) config.getUserProperties().get(HttpSession.class.getName());

        this.httpSession.setAttribute("user","zs");

        String user=(String) this.httpSession.getAttribute("user");
        this.session=session;

        log.info("user is [{}]",user);

        log.info("sessionId is [{}]",this.session.getId());

        if(map.get(user)!=null){
            map.remove(user);
            subOnlineCount();
        }

        map.put(user,this);

        addOnlineCount();

        log.info("有新的连接，当前连接数未[{}]",getOnlineCount());

    }

    @OnClose
    public void onClose(){
        String user=(String) this.httpSession.getAttribute("user");
        map.remove(user);
        subOnlineCount();
        log.info("有一个连接断开，当前连接数未[{}]",getOnlineCount());
    }


    @OnMessage
    public void onMessage(String message) throws IOException{
        WSServer _client=map.get("zs");
        log.info("后端发送数据开始");
        if(null!=_client){
            if(_client.session.isOpen()){
                try {
                    Thread.sleep(10000);
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


    static
    public void pushBySys(){
        WSServer ws=map.get("zs");
        try {
            if(ws!=null){
                ws.session.getBasicRemote().sendText("123456");
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }


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

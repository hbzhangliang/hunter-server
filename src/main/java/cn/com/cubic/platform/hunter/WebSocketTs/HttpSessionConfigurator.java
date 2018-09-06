package cn.com.cubic.platform.hunter.WebSocketTs;

import org.springframework.web.socket.server.standard.SpringConfigurator;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

/**
 * Created by Liang.Zhang on 2018/7/4.
 **/

public class HttpSessionConfigurator  extends ServerEndpointConfig.Configurator{


    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        HttpSession httpSession=(HttpSession) request.getHttpSession();
        if(httpSession!=null){
            sec.getUserProperties().put(HttpSession.class.getName(),httpSession);
        }

    }
}

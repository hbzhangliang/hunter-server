package cn.com.cubic.platform.test.socket;

import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.net.Socket;

/**
 * Created by Liang.Zhang on 2018/9/6.
 **/

public class serverThread implements Runnable{

    private Socket socket;


    public serverThread(Socket socket){
        this.socket=socket;
    }


    @Override
    public void run() {
        try {
            InputStream is = socket.getInputStream();
            InputStreamReader isr =new InputStreamReader(is);
            BufferedReader br =new BufferedReader(isr);
            String info =null;
            while((info=br.readLine())!=null){
                System.out.println("我是服务器，客户端说："+info);
            }
            socket.shutdownInput();//关闭输入流

            OutputStream os = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(os);

            Msg msg=new Msg("zs","ls","这是测试数据");
            pw.write(JSONObject.toJSONString(msg));
            pw.flush();
            pw.close();
            os.close();
            br.close();
            isr.close();
            is.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}

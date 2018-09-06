package cn.com.cubic.platform.test.socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Liang.Zhang on 2018/9/6.
 **/

public class SocketServer {

    public static void main(String[] args){

        try {
            ServerSocket serverSocket = new ServerSocket(10086);//1024-65535的某个端口
            Socket socket =null;
            int count =0;//记录客户端的数量
            while (true) {
                socket=serverSocket.accept();
                serverThread serverThread =new serverThread(socket);
                Thread thread=new Thread(serverThread);
                thread.start();
                count++;
                System.out.println("客户端连接的数量："+count);
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }


    }


    public static void startSocketServer() throws Exception{
        /**
         * 基于TCP协议的Socket通信，实现用户登录，服务端
         */
//1、创建一个服务器端Socket，即ServerSocket，指定绑定的端口，并监听此端口
        ServerSocket serverSocket =new ServerSocket(10086);//1024-65535的某个端口
//2、调用accept()方法开始监听，等待客户端的连接
        Socket socket = serverSocket.accept();
//3、获取输入流，并读取客户端信息
        InputStream is = socket.getInputStream();
        InputStreamReader isr =new InputStreamReader(is);
        BufferedReader br =new BufferedReader(isr);
        String info =null;
        while((info=br.readLine())!=null){
            System.out.println("我是服务器，客户端说："+info);
        }
        socket.shutdownInput();//关闭输入流
//4、获取输出流，响应客户端的请求
        OutputStream os = socket.getOutputStream();
        PrintWriter pw = new PrintWriter(os);
        pw.write("欢迎您！");
        pw.flush();


//5、关闭资源
        pw.close();
        os.close();
        br.close();
        isr.close();
        is.close();
        socket.close();
        serverSocket.close();
    }



    public static void taskSocketServer(){

    }

}

package cn.com.cubic.platform.test;

import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SsyncTest001 {

    public static void main(String[] args) throws Exception{
        System.out.println("====进入主线程执行任务");

        //通过线程池管理多线程
        ExecutorService threadPool = Executors.newCachedThreadPool();

        //线程池提交一个异步任务
        System.out.println("====提交异步任务");
        Future<HashMap<String,String>> future = threadPool.submit(new Callable<HashMap<String,String>>() {

            @Override
            public HashMap<String,String> call() throws Exception {

                System.out.println("异步任务开始执行....");
                Thread.sleep(5000);
                System.out.println("异步任务执行完毕，返回执行结果!!!!");

                return new HashMap<String,String>(){
                    {this.put("futureKey", "成功获取future异步任务结果");}
                };
            }

        });

        System.out.println("====提交异步任务之后，立马返回到主线程继续往下执行");
        Thread.sleep(1000);

        System.out.println("====此时需要获取上面异步任务的执行结果");

        boolean flag = true;
        while(flag){
            System.out.println("dodo-------");
            //异步任务完成并且未被取消，则获取返回的结果
            if(future.isDone() && !future.isCancelled()){
                HashMap<String,String> futureResult = future.get();
                System.out.println("====异步任务返回的结果是："+futureResult.get("futureKey"));
                flag = false;
            }
            Thread.sleep(500);
        }

        //关闭线程池
        if(!threadPool.isShutdown()){
            threadPool.shutdown();
        }
    }




}

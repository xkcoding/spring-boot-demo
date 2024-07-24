package com.example.awaysuse.thread;


import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MyTest {
    public static void main(String[] args) {
        //1:创建线程池类对象;
      /*  MyThreadPool pool = new MyThreadPool(2, 4, 20);
        //2: 提交多个任务
        for (int i = 0; i < 30; i++) {
            //3:创建任务对象,并提交给线程池
            MyTask my = new MyTask(i);
            pool.submit(my);
        }*/

        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        /*executorService.scheduleAtFixedRate(()->{
            try {
                //加上这个，看看1没执行完，会不会马上执行2，意即会不会开个新线程去执行
                Thread.sleep(1000);
            }catch (Exception ex){

            }
            System.out.println(Thread.currentThread().getName()+" run : "+ System.currentTimeMillis());
        }, 0, 100, TimeUnit.MILLISECONDS);*/
        //0延时，每隔100毫秒执行一次
        executorService.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("俩人相视一笑~ 嘿嘿嘿");
            }
        }, 10, TimeUnit.SECONDS);
    }



}

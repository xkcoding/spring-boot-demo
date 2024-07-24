package com.xkcoding.async.task;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class ThreeDemo3 {

    public static void main(String[] args) {
        // 创建任务对象
        Callable   callable=new MyCallAble(10);
        // 把任务对象交给线程对象处理
        FutureTask<String> futureTask=new FutureTask<>(callable);
        Thread t1 = new Thread(futureTask);
        t1.start();

        try {
            String s = futureTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }
}


class MyCallAble implements Callable<String>{
    private int n;

    public MyCallAble(int n) {
        this.n = n;
    }

    @Override
    public String call() throws Exception {
        int sum=0;
        for (int i = 0; i < n; i++) {
            sum+=i;
        }
        return "累加之后的值是"+sum;
    }
}

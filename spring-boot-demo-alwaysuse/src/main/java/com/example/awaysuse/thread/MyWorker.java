package com.example.awaysuse.thread;

import java.util.List;

public class MyWorker extends  Thread{


    private String name;//保存线程的名字
    private List<Runnable> tasks;
    //利用构造方法,给成员变量赋值

    public MyWorker(String name, List<Runnable> tasks) {
        super(name);
        this.tasks = tasks;
    }

    @Override
    public void run() {
        //判断集合中是否有任务,只要有,就一直执行任务
        while (tasks.size()>0){
            Runnable r = tasks.remove(0);
            r.run();
        }
    }

}

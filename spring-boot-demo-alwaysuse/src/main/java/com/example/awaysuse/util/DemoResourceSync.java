package com.example.awaysuse.util;


import com.alibaba.fastjson.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DemoResourceSync {

    public static void main(String[] args) {
        DemoResourceSync dr = new DemoResourceSync();
        dr.run();
    }

    public void run() {
        ResThread resThread = new ResThread();
        for (int i = 0; i < 10; i++) { //10个线程去跑
            new Thread(resThread, "线程" + i).start();
        }

        PerfThread perfThread = new PerfThread();
        for (int i = 0; i < 20; i++) { //20个线程去跑
            new Thread(perfThread, "线程" + i).start();
        }

        StateThread stateThread = new StateThread();
        for (int i = 0; i < 20; i++) { //20个线程去跑
            new Thread(stateThread, "线程" + i).start();
        }
    }

    /**
     * 同步资源
     * @author Administrator
     *
     */
    private class ResThread implements Runnable {

        public ResThread() {
        }

        @Override
        public void run() {
            synchronized (this) {
                SimpleDateFormat sdf = new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss");
                String datestr = sdf.format(new Date());
                String str = "[";
                for (int i = 0; i < 900; i++) {
                    str += "{\"classCode\":\"Switch\",\"domainId\":\"rootDomain\",\"sourceId\":\"192.168."
                        + Thread.currentThread().getId()
                        + "."
                        + i
                        + "\",\"updateTime\":\""
                        + datestr
                        + "\",\"values\":{\"name\":\"交换机"
                        + Thread.currentThread().getId()
                        + i
                        + "\",\"ipAddr\":\"192.168."
                        + Thread.currentThread().getId()
                        + "."
                        + i
                        + "\"}},";
                }
                str = str.substring(0, str.length() - 1) + "]";
                System.out.println("resThread:"+str);
                String url = "http://127.0.0.1:8890/api/v2/cmdb/cis/save-batch";
                try {
                    JSONObject jsonObject = HttpClientUtils.httpPost(url, str);
                    String result = jsonObject.getString("result");
                    System.out.println("resThreadResult"
                        + Thread.currentThread().getId() + ":" + result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 同步性能
     * @author Administrator
     *
     */
    private class PerfThread implements Runnable {

        public PerfThread() {
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();

        @Override
        public void run() {
            synchronized (this) {
                String str = "[";
                for (int i = 500; i > 0; i--) {
                    String datestr = sdf.format(new Date((calendar
                        .getTimeInMillis() - (1000 * Thread.currentThread()
                        .getId() * (i + 1)))));
                    str += "{\"ciId\":\"3a98052b-794c-4531-9182-611625c102a4\",\"groupCode\":\"ram-use\","
                        + "\"sampleTime\":\""
                        + datestr
                        + "\",\"indicators\":{\"mem_used\":500,\"mem_usage\":97}},";
                }
                str = str.substring(0, str.length() - 1) + "]";
                System.out.println("perfThread:"+str);
                String url = "http://127.0.0.1:8890/api/v2/pmdb/perf-groups/create";
                try {
                    JSONObject jsonObject = HttpClientUtils.httpPost(url, str);
                    String result = jsonObject.getString("result");
                    System.out.println("perfThreadResult"
                        + Thread.currentThread().getId() + ":" + result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 同步状态
     * @author Administrator
     *
     */
    private class StateThread implements Runnable {

        public StateThread() {
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();

        @Override
        public void run() {
            synchronized (this) {
                String str = "[";
                for (int i = 500; i > 0; i--) {
                    String datestr = sdf.format(new Date((calendar
                        .getTimeInMillis() - (1000 * Thread.currentThread()
                        .getId() * (i + 1)))));
                    str += "{\"ciId\":\"3a98052b-794c-4531-9182-611625c102a4\",\"typeCode\":\"available_status\","
                        + "\"sampleTime\":\""
                        + datestr
                        + "\",\"value\":\"1\",\"descr\":\"可用\"},";
                }
                str = str.substring(0, str.length() - 1) + "]";
                System.out.println("stateThread:"+str);
                String url = "http://127.0.0.1:8890/api/v2/pmdb/states/create";
                try {
                    JSONObject jsonObject = HttpClientUtils.httpPost(url, str);
                    String result = jsonObject.getString("result");
                    System.out.println("stateThreadResult"
                        + Thread.currentThread().getId() + ":" + result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


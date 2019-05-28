package com.liyh.app.okhttp;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Yahri Lee
 * @date 2019 年 05 月 21 日
 * @time 15 时 32 分
 * @descrip :
 */
@RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
public class ThreadPoolManager {
    private static ThreadPoolManager threadPoolManager = new ThreadPoolManager();

    public static ThreadPoolManager getInstance() {
        return threadPoolManager;
    }


    //创建队列，将网络请求添加到队列中
    private LinkedBlockingDeque<Runnable> mQueue = new LinkedBlockingDeque();

    public void addTask(Runnable task) {
        if (task != null) {
            try {
                mQueue.put(task);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //创建线程池
    public ThreadPoolExecutor mThreadPoolExecutor = null;

    private ThreadPoolManager() {
        mThreadPoolExecutor = new ThreadPoolExecutor(3, 10, 1500, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(4),
                new RejectedExecutionHandler() {
                    @Override
                    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                        //将拒绝的线程 重新返回队列
                        addTask(r);
                    }
                });
        mThreadPoolExecutor.execute(coreThread);
        mThreadPoolExecutor.execute(delayThread);
    }

    //创建叫号线程不停的获取
    public Runnable coreThread = new Runnable() {

        private Runnable runn = null;
        @Override
        public void run() {
            while (true){
                try {
                    runn = mQueue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mThreadPoolExecutor.execute(runn);
            }
        }
    };

    //创建延迟队列
    private DelayQueue<HttpTask> mDelayQueue = new DelayQueue<>();

    public void addDelayTask(HttpTask delayTask){
        if (delayTask != null){
            delayTask.setDelayTime(3000);
            mDelayQueue.offer(delayTask);
        }
    }

    //创建延迟线程
    private Runnable delayThread = new Runnable() {
        HttpTask delayTask = null;
        @Override
        public void run() {
            while (true){
                try {
                    delayTask = mDelayQueue.take();
                    //如果当前任务重试次数小于3次，继续将其交给线程池处理，否则直接放弃
                    if (delayTask.getRetryCount() < 3){
                        mThreadPoolExecutor.execute(delayTask);
                        delayTask.setRetryCount(delayTask.getRetryCount() + 1);
                        Log.e("===>", "重试机制===:" + delayTask.getRetryCount() + "   " + System.currentTimeMillis());
                    } else {
                        Log.e("===>", "重试机制===:"  + "重试超过3次，直接放弃");
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };
}

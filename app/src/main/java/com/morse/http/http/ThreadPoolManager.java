package com.morse.http.http;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by morse on 2018/1/20.
 * 1.把任务添加到请求队列
 * 2.吧队列中的任务放到线程池
 * 3.任务线程自动
 */
public class ThreadPoolManager {

    private static ThreadPoolManager instance;

    private LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();

    public void execute(Runnable runnable) {
        if (null != runnable) {
            try {
                queue.put(runnable);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private ThreadPoolExecutor threadPoolExecutor;
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            while (true) {
                Runnable runnable = null;
                //从队列获取请求
                try {
                    runnable = queue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (null != runnable) {
                    threadPoolExecutor.execute(runnable);
                }
            }
        }
    };

    private ThreadPoolManager() {
        threadPoolExecutor = new ThreadPoolExecutor(4, 20,
                15, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(4),
                rejectedExecutionHandler);

        threadPoolExecutor.execute(runnable);
    }

    private RejectedExecutionHandler rejectedExecutionHandler = new RejectedExecutionHandler() {
        /**
         *
         * @param r 超时的线程
         * @param executor
         */
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            try {
                queue.put(r);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    public static ThreadPoolManager getInstance() {
        if (null == instance) {
            synchronized (ThreadPoolManager.class) {
                if (null == instance) {
                    return instance = new ThreadPoolManager();
                }
            }
        }
        return instance;
    }
}

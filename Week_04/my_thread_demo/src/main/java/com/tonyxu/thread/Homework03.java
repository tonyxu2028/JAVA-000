package com.tonyxu.thread;

import com.tonyxu.thread.util.FiboUtil;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import static com.tonyxu.thread.constant.ThreadConstant.THREAD_COUNT_DOWN_LATCH_TYPE;
import static com.tonyxu.thread.constant.ThreadConstant.THREAD_CYCLIC_BARRIER_TYPE;
import static com.tonyxu.thread.constant.ThreadConstant.THREAD_SEMAPHORE_NO_TOOLS_TYPE;
import static com.tonyxu.thread.constant.ThreadConstant.THREAD_SEMAPHORE_TOOLS_TYPE;

/**
 * Created on 2020/11/9.
 *
 * @author <a href="191284969@qq.com">Tony xu</a>
 */
public class Homework03 {

    public static void main(String[] args) {

        try {

            if (args.length != 2) {
                return;
            }

            String type = args[0];
            int taskCount = Integer.parseInt(args[1]);

            // CyclicBarrierType
            if (THREAD_CYCLIC_BARRIER_TYPE.equals(type)) {
                cyclicBarrierType(taskCount);
            }

            // CountDownLatchType
            if(THREAD_COUNT_DOWN_LATCH_TYPE.equals(type)){
                countDownLatchType(taskCount);
            }

            // SemaphoreToolsType
            if(THREAD_SEMAPHORE_TOOLS_TYPE.equals(type)){
                semaphoreToolsType(taskCount);
            }

            // SemaphoreNoToolsType
            if(THREAD_SEMAPHORE_NO_TOOLS_TYPE.equals(type)){
                semaphoreNoToolsType(taskCount);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    //***************************** CyclicBarrierType ******************************//

    private static void cyclicBarrierType(int taskCount){

            long start = System.currentTimeMillis();

            CyclicBarrier cyclicBarrier = new CyclicBarrier(taskCount, new Runnable() {
                @Override
                public void run() {
                    System.out.println("回调>>" + Thread.currentThread().getName());
                    // 异步执行下面方法
                    int result = FiboUtil.sum(); //这是得到的返回值
                    // 确保拿到result 并输出
                    System.out.println("异步计算结果为：" + result);
                    System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
                    System.out.println("回调>>线程组执行结束");
                }
            });

            // 在这里创建一个线程或线程池
            for (int i = 0; i < taskCount; i++) {
                new Thread(new ReadCyclicBarrierNum(i, cyclicBarrier)).start();
            }

    }

    static class ReadCyclicBarrierNum  implements Runnable{
        private int id;
        private CyclicBarrier cyc;
        public ReadCyclicBarrierNum(int id,CyclicBarrier cyc){
            this.id = id;
            this.cyc = cyc;
        }
        @Override
        public void run() {
            synchronized (this){
                try {
                    System.out.println("CyclicBarrierType:::线程组任务" + id + "已经结束");
                    cyc.await();   // 注意跟CountDownLatch不同，这里在子线程await
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //***************************** CountDownLatchType ******************************//

    private static void countDownLatchType(int taskCount){

            long start = System.currentTimeMillis();

            CountDownLatch countDownLatch = new CountDownLatch(taskCount);
            for (int i = 0; i < taskCount; i++) {
                new Thread(new ReadCountDownLatchNum(i, countDownLatch)).start();
            }

            try {
                countDownLatch.await(); // 注意跟CyclicBarrier不同，这里在主线程await
                int result = FiboUtil.sum(); //这是得到的返回值
                System.out.println("异步计算结果为：" + result);
                System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
                System.out.println("==>各个子线程执行结束。。。。");
                System.out.println("==>主线程执行结束。。。。");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

    }

    static class ReadCountDownLatchNum  implements Runnable{
        private int id;
        private CountDownLatch latch;
        public ReadCountDownLatchNum(int id,CountDownLatch latch){
            this.id = id;
            this.latch = latch;
        }
        @Override
        public void run() {
            synchronized (this){
                System.out.println("CountDownLatch:::线程组任务"+id+"已经结束");
                latch.countDown();
            }
        }
    }

    //***************************** SemaphoreToolsType ******************************//

    private static void semaphoreToolsType(int taskCount){

        long start = System.currentTimeMillis();

        ExecutorService exec = Executors.newCachedThreadPool();

        try {

            final Semaphore semaphore = new Semaphore(3);
            for (int i = 0; i < taskCount; i++) {
                final int threadNum = i;
                exec.execute(() -> {
                    try {
                        semaphore.acquire(3); // 获取全部许可,进行串行处理看的明白
                        readSemaphoreNum(threadNum, start);
                        semaphore.release(3); // 释放多个许可
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        }finally {
            exec.shutdown();
        }

    }

    private static void readSemaphoreNum (int threadNum,long start) throws Exception {
        System.out.println("SemaphoreToolsType:::线程组任务"+threadNum+"获取结果");
        // 异步执行下面方法
        int result = FiboUtil.sum(); //这是得到的返回值
        // 确保拿到result 并输出
        System.out.println("异步计算结果为："+result);
        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
        System.out.println("SemaphoreToolsType:::线程组任务"+threadNum+"结束关闭");
        System.out.println();
        Thread.sleep(1000);
    }

    //***************************** SemaphoreNoToolsType ******************************//

    private static void semaphoreNoToolsType(int taskCount){

        long start = System.currentTimeMillis();

        try {
            Semaphore semaphore = new Semaphore(3);
            for (int i = 0; i < taskCount; i++) {
                semaphore.acquire(3);
                new Worker(i, semaphore, start).start();
                semaphore.release(3);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    static class Worker extends Thread {
        private int num;
        private Semaphore semaphore;
        private long start;

        public Worker(int num, Semaphore semaphore,long start) {
            this.num = num;
            this.semaphore = semaphore;
            this.start = start;
        }

        @Override
        public void run() {
            try {
                // 异步执行下面方法
                int result = FiboUtil.sum(); //这是得到的返回值
                // 确保拿到result 并输出
                System.out.println("SemaphoreNoToolsType:::线程组任务" + num + "获取结果");
                System.out.println("异步计算结果为：" + result);
                System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
                System.out.println("SemaphoreNoToolsType:::线程组任务" + num + "结束关闭");
                System.out.println();
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}

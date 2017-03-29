package cn.zxw.thread.pool;

import java.util.concurrent.*;

/**
 * Created by zhangxw on 2017/3/28.
 */
public class LQExecutorService {
    private static ExecutorService playService = new ThreadPoolExecutor(5, 5, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
    private static ArrayBlockingQueue<String> cacheQueue = new ArrayBlockingQueue<String>(20);
    private static ScheduledExecutorService checkService  = Executors.newScheduledThreadPool(1);

    public void execute(){
        for(int i=0;i<5;i++){
            playService.submit(new LogService());
        }
        System.out.println("playService threads start ...");
    }

    static class LogService implements Runnable{
        public void run() {
            while (true){
                try {
                    String message = cacheQueue.take();
                    System.out.println(Thread.currentThread().getName()+" take "+message);
                    Thread.sleep((long)Math.random()*1000+1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void check(){
        checkService.scheduleAtFixedRate(new Runnable() {
            public void run() {
                //获取queue 当前容量
                int flowQueueSize = cacheQueue.size();
                System.out.println("check queue size "+flowQueueSize);
            }
        },0,1000, TimeUnit.MILLISECONDS);
    }

    private static void addHook(){
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run(){
                shutdown();
            }
        });
    }

    public static void shutdown(){
        if (playService != null) playService.shutdown();
        try {
            if (!playService.awaitTermination(5000, TimeUnit.MILLISECONDS)) {
                System.out.println("Timed out waiting for playService threads to shut down, exiting uncleanly");
            }
        } catch (InterruptedException e) {
            //System.out.println("Interrupted during shutdown, exiting uncleanly");
        }
        System.out.println("playService threads shutdown");
    }

    public static void main(String[] args){
        LQExecutorService obj = new LQExecutorService();
        obj.execute();

        new Thread(new Runnable() {
            public void run() {
                while (true) {
                    try {
                        cacheQueue.put("data");
                        Thread.sleep((long)Math.random()*1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        obj.check();

        addHook();
    }
}

package cn.zxw.thread;

public class TraditionalThreadCommunication {
    public static void main(String[] args) {
       final Business business = new Business();
       new Thread(
           new Runnable() {
              public void run() {
                  for(int i=1;i<=10;i++){
                     business.sub(i);
                  }
              }
           }
       ).start();
      
       for(int i=1;i<=10;i++){
           business.main(i);
       }
    }
}
 
class Business {
    private boolean bShouldSub = true;
    public synchronized void sub(int i) {
       while (!bShouldSub) {
           try {
              this.wait();
           } catch (InterruptedException e) {
              e.printStackTrace();
           }
       }
       for (int j = 1; j <= 5; j++) {
           System.out.println("sub thread " + j + ",loop of " + i);
       }
       bShouldSub = false;
       this.notify();
    }
 
    public synchronized void main(int i) {
       while (bShouldSub) {
           try {
              this.wait();
           } catch (InterruptedException e) {
              e.printStackTrace();
           }
       }
       for (int j = 1; j <= 10; j++) {
           System.out.println("main thread " + j + ",loop of " + i);
       }
       bShouldSub = true;
       this.notify();
    }
}
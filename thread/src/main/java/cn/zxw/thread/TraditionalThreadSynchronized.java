package cn.zxw.thread;

public class TraditionalThreadSynchronized {
 
    public static void main(String[] args) {
       new TraditionalThreadSynchronized().init();
    }
   
    private void init(){
       final Outputer outputer = new Outputer();
       new Thread(new Runnable(){
           public void run() {
              while(true){
                  try {
                     Thread.sleep(1);
                  } catch (InterruptedException e) {
                     e.printStackTrace();
                  }
                  outputer.output("zhangxiaoxiang");
              }            
           }
       }).start();
      
       new Thread(new Runnable(){
           public void run() {
              while(true){
                  try {
                     Thread.sleep(1);
                  } catch (InterruptedException e) {
                     e.printStackTrace();
                  }
                  outputer.output("lihuoming");
              }  
           }
       }).start();
    }
 
    static class Outputer{     
       public void output(String name){
           int len = name.length();
           synchronized (Outputer.class)
           {
              for(int i=0;i<len;i++){
                  System.out.print(name.charAt(i));
              }
              System.out.println();
           }
       }
      
       public synchronized void output2(String name){
           int len = name.length();
           for(int i=0;i<len;i++){
                  System.out.print(name.charAt(i));
           }
           System.out.println();
       }
      
       public static synchronized void output3(String name){
           int len = name.length();
           for(int i=0;i<len;i++){
                  System.out.print(name.charAt(i));
           }
           System.out.println();
       }
    }
}
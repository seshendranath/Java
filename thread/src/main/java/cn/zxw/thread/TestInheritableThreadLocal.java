package cn.zxw.thread;

import junit.framework.TestCase;

/**
 * 比较后，看到ThreadLocal里的值，子线程里不能获得；InheritableThreadLocal里的值，子线程可以获得
 */
public class TestInheritableThreadLocal extends TestCase{
	public void testThreadLocal() {
		final ThreadLocal<String> local = new ThreadLocal<String>();
		work(local);
	}

	public void testInheritableThreadLocal() {
		final ThreadLocal<String> local = new InheritableThreadLocal<String>();
		work(local);
	}
	
	private void work(final ThreadLocal<String> local) {
		local.set("a");
		System.out.println(Thread.currentThread() + "," + local.get());
		Thread t = new Thread(new Runnable() {
			public void run() {
				System.out.println(Thread.currentThread() + "," + local.get());
				local.set("b");
				System.out.println(Thread.currentThread() + "," + local.get());
			}
		});
		t.start();
		try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread() + "," + local.get());
	}
}

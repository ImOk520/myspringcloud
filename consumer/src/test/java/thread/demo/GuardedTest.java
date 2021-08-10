package thread.demo;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Console;

import java.io.IOException;
import java.util.List;

public class GuardedTest {
    // t1 等待 t2线程的下载结果
    public static void main(String[] args) {
        // 这里的使用同一个guardeObject对象
        // 原因1：保护性暂停模式，两个线程的使用同一个guardeObject对象
        // 原因2：代码设计到synchronized，所以多个线程要对同一个对象进行操作
        // 以确保共享成员变量的安全性(这里的共享成员变量是GuardeObject中的response对象)
        GuardedObject guardeObject = new GuardedObject();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Console.log(DateUtil.now() +Thread.currentThread().getName() + ":等待结果");
                try {
                    // 等待结果：至于等待多久得看线程2啥时候下载完
                    List<String> list = (List<String>)guardeObject.get();
                    Console.log(DateUtil.now() +Thread.currentThread().getName() + ":结果大小" + list.size());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "t1").start();


        new Thread(()->{
            Console.log(DateUtil.now() +Thread.currentThread().getName() + ":执行下载");
            try {
                List<String> list = DownLoader.download();
                // 将结果传递给GuardeObject，并唤醒正在等待的线程
                guardeObject.complete(list);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
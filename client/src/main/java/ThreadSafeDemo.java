import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadSafeDemo {

    private static int anInt = 0;

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            executorService.submit(() -> doSomeHeavyWork());

        }

    }

    private static void doSomeHeavyWork() {
        System.out.println(Thread.currentThread().getName() + ":" + anInt);
    }
}

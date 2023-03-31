import java.util.*;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        String[] texts = new String[25];
        ExecutorService threadPool = Executors.newFixedThreadPool(texts.length);
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("aab", 30_000);
        }
        long startTs = System.currentTimeMillis(); // start time
        List <Future> list = new ArrayList<>();
        for (String text : texts) {
            Future callable = threadPool.submit(new MyCallable(text));
            list.add(callable);
        }
        int max = 0;
        for (Future future : list) {
            int temp = (int) future.get();
            if (max < temp) {
                max = temp;
            }
        }
        long endTs = System.currentTimeMillis(); // end time
        System.out.println("Time: " + (endTs - startTs) + "ms");
        threadPool.shutdown();
        System.out.println(max);
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}

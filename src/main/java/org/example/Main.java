package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static AtomicInteger beautiful3 = new AtomicInteger(0);
    public static AtomicInteger beautiful4 = new AtomicInteger(0);
    public static AtomicInteger beautiful5 = new AtomicInteger(0);


    public static void main(String[] args) {
        List<Thread> threads = new ArrayList<>();
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }


        Thread thread1 = new Thread(() -> {  // abba
            for (String s : texts) {
                boolean same = isSame(s);
                if (same) {
                    continue;
                }

                if (s.equals(new StringBuilder(s).reverse().toString())) {
                    countBeautiful(s);
                }
            }
        });
        threads.add(thread1);

        Thread thread2 = new Thread(() -> {  // bbb
            for (String s : texts) {
                boolean same = isSame(s);

                if (same) {
                    countBeautiful(s);
                }
            }
        });
        threads.add(thread2);

        Thread thread3 = new Thread(() -> {  // bbccc
            for (String s : texts) {
                char[] chars = s.toCharArray();
                boolean same = isSame(s);
                if (same) {
                    continue;
                }

                boolean check = true;
                for (int i = 0; i < chars.length - 1; i++) {
                    if (!(chars[i] <= chars[i + 1])) {
                        check = false;
                        break;
                    }
                }

                if (check) {
                    countBeautiful(s);
                }
            }
        });
        threads.add(thread3);

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("Красивых слов с длиной 3: " + beautiful3.get() + " шт.");
        System.out.println("Красивых слов с длиной 4: " + beautiful4.get() + " шт.");
        System.out.println("Красивых слов с длиной 5: " + beautiful5.get() + " шт.");
    }

    public static boolean isSame(String s) {
        boolean same = true;
        for (char c : s.toCharArray()) {
            if (!(c == s.charAt(0))) {
                same = false;
                break;
            }
        }

        return same;
    }

    public static void countBeautiful(String s) {
        switch (s.length()) {
            case (3):
                beautiful3.getAndIncrement();
                break;
            case (4):
                beautiful4.getAndIncrement();
                break;
            case (5):
                beautiful5.getAndIncrement();
                break;
            default:
                System.out.println("Something went wrong");
                break;
        }
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
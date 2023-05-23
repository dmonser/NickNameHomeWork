package org.example;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static AtomicInteger beautiful3;
    public static AtomicInteger beautiful4;
    public static AtomicInteger beautiful5;


    public static void main(String[] args) {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }


//        new Thread(() -> {  // abba
//            for (String s : texts) {
//                if (s.equals(new StringBuilder(s).reverse().toString())){
//                    System.out.println(s);
//                }
//            }
//        }).start();

//        new Thread(() -> {  // fff
//           for (String s : texts) {
//                char c = s.charAt(0);
//                boolean same = true;
//                for (char check : s.toCharArray()) {
//                    if (!(check == c)) {
//                        same = false;
//                    }
//                }
//                if (same){
//                    System.out.println(s);
//                }
//           }
//        }).start();

        new Thread(() -> {  // ppfff
           for (String s : texts) {
               char[] chars = s.toCharArray();
               boolean same = true;

               for (int i = 0; i < chars.length - 1; i++) {
                   char next = chars[i + 1];
                   if (chars[i] == next || chars[i] == (next += 1)) {
                       continue;
                   } else {
                       same = false;
                       break;
                   }
               }

               if (same) {
                   System.out.println(s);
               }
           }
        }).start();
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
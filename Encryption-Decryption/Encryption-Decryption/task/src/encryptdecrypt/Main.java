package encryptdecrypt;

import java.util.Scanner;

public class Main {
    static String encryptText_stage1(String text) {
        StringBuilder result = new StringBuilder();
        for (char c: text.toCharArray()) {
            if (Character.isLetter(c)) {
                result.append((char)(219 - c));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

    static String encryptText_stage2(String text, int key) {
        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        StringBuilder result = new StringBuilder();
        int idx;
        for (char c: text.toCharArray()) {
            if (Character.isLetter(c)) {
                idx = (c - 97 + key) % 26;
                result.append(alphabet[idx]);
            } else {
                result.append(c);
            }
        }

        return result.toString();
    }

    static void stage1() {
        String message = "we found a treasure!";
        System.out.println(encryptText_stage1(message));
    }

    static void stage2() {
        Scanner sc = new Scanner(System.in);
        String text = sc.nextLine().trim();
        int key = sc.nextInt();
        System.out.println(encryptText_stage2(text, key));
    }
    public static void main(String[] args) {
        stage1();
        stage2();
    }
}

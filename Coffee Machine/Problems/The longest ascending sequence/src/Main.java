import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int len = sc.nextInt();
        int[] arr = new int[len];
        for (int i = 0; i < len; i++) {
            arr[i] = sc.nextInt();
        }
        int maxLen = 1;
        int current = 1;
        for (int i = 1; i < len; i++) {
            if (arr[i - 1] < arr[i]) {
                current++;    
            } else {
                maxLen = Math.max(current, maxLen);
                current = 1;
            }
        }
        System.out.println(Math.max(maxLen, current));
    }
}

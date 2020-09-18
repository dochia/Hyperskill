import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

class Main {

    public static void decompose(int n, int k, String result) {

        if (n > 1) {
            decompose(n - 1, k, result);
        }
        result += n + " ";
        if (k - n > 0) {
            int min = Math.min(k - n, n);
            decompose(min, k - n, result);
        }
        if (k - n == 0) {
            System.out.println(result);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        decompose(n, n, "");
    }
}

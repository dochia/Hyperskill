import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int a = scanner.nextInt();
        int b = scanner.nextInt();

        Random r = new Random(a + b);
        long sum = 0;
        int i = -1;
        while (++i < n) {
            sum += r.nextInt(b - a + 1) + a;
        }
        System.out.println(sum);
    }
}

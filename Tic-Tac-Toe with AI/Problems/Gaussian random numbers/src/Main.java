import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int k = scanner.nextInt();
        int n = scanner.nextInt();
        double m = scanner.nextDouble();

        Random random = new Random(k);
        boolean found = false;
        int i = 0;
        while (!found) {
            i = -1;
            while (++i < n) {
                if (random.nextGaussian() > m) {
                    found = false;
                    k++;
                    random = new Random(k);
                    break;
                }
            }
            if (i == n) {
                found = true;
            }
        }
        System.out.println(k);
    }
}
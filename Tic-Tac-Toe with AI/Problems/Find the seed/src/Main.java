import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int a = scanner.nextInt();
        int b = scanner.nextInt();
        int n = scanner.nextInt();
        int k = scanner.nextInt();

        int[] seeds = new int[b - a + 1];
        Arrays.fill(seeds, Integer.MIN_VALUE);

        int max;
        Random r;
        for (int s = a; s <= b; s++) {
            r =  new Random(s);
            max = Integer.MIN_VALUE;
            for (int i = 0; i < n; i++) {
                max = Math.max(max, r.nextInt(k));
            }
            seeds[s - a] = max;
        }
        int i = a;
        int min = seeds[0];
        int location = 0;
        for (int value: seeds) {
            if (value < min) {
                location = i;
                min = value;
            }
            i++;
        }
        System.out.println(location);
        System.out.println(min);
    }
}
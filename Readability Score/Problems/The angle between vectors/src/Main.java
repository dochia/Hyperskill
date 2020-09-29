import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int x1 = sc.nextInt();
        int y1 = sc.nextInt();
        int x2 = sc.nextInt();
        int y2 = sc.nextInt();
        double product = (double) x1 * x2 + (double) y1 * y2;
        double length1 = Math.hypot(x1, y1);
        double length2 = Math.hypot(x2, y2);
        System.out.println(Math.toDegrees(Math.acos(product / (length1 * length2))));
    }
}

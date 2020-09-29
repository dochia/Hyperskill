import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        double a = sc.nextDouble();
        double b = sc.nextDouble();
        double c = sc.nextDouble();
        resolveQuadraticEquation(a, b, c);
    }

    private static void resolveQuadraticEquation(double a, double b, double c) {
        double delta = Math.sqrt(b * b - 4 * a * c);
        double sol1 = (b * -1 + delta) / (2 * a);
        double sol2 = (b * -1 - delta) / (2 * a);
        System.out.printf("%f %f %n", Math.min(sol1, sol2), Math.max(sol1, sol2));
    }
}
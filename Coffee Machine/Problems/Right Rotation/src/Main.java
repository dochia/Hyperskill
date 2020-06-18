import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] numbers = sc.nextLine().trim().split(" ");
        int n = numbers.length;
        int rotation = sc.nextInt() % n;
        
        for (int i = 0; i < rotation; i++) {
            System.out.printf("%s ", numbers[n - rotation + i]);
        }
        for (int i = rotation; i < n; i++) {
            System.out.printf("%s ", numbers[i - rotation]);
        }
    }
}

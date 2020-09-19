package converter;

import java.math.BigInteger;
import java.util.BitSet;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.function.BiFunction;

public class Main {
    static char[] digits = new char[]{
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
            'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z'
    };

    private static String decimalToBinary(int value) {
        String result = decimalToX(value, 2);
        return "0b" + result;
    }

    private static String decimalToX(int value, int base) {
        if (value == 0) {
            return "0";
        }
        StringBuilder sb = new StringBuilder();
        while (value > 0) {
            sb.append(digits[value % base]);
            value /= base;
        }
        return sb.reverse().toString();
    }

    private static double decimalStringToX(String value, int base) {
        int numerator = Integer.parseInt(value, base);
        return (double)numerator / Math.pow(base, value.length());
    }

    private static int getLastDigitInBase8(int value) {
        String result = decimalToX(value, 8);
        return result.charAt(result.length() - 1) - 48;
    }

    private static void stage2() {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        System.out.println(String.format("%d", getLastDigitInBase8(n)));
    }

    private static void stage3() {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int base = sc.nextInt();
        String result = decimalToX(n, base);
        switch (base) {
            case 8:
                result = "0" + result;
                break;
            case 2:
                result = "0b" + result;
                break;
            case 16:
                result = "0x" + result;
                break;
            default:
                System.out.println(String.format("Decimal to base %d not implemented!", base));

        }
        System.out.println(result);
    }

    private static void stage4() {
        Scanner sc = new Scanner(System.in);
        int sourceRadix = Integer.parseInt(sc.nextLine().trim());
        String number = sc.nextLine().trim();
        int targetRadix = Integer.parseInt(sc.nextLine().trim());
        int value;
        switch (sourceRadix) {
            case 1:
                value = Integer.parseInt(String.valueOf(number.length()));
                break;
            case 10:
                value = Integer.parseInt(number);
                break;
            default:
                value = Integer.parseInt(number, sourceRadix);
        }
        String result;
        switch (targetRadix) {
            case 1:
                result = "1".repeat(value);
                break;
            default:
                result = Integer.toString(value, targetRadix);
        }
        System.out.println(result);
    }

    private static String convertFractionalToX(double fract10, int target) {
        StringBuilder sb = new StringBuilder();
        double value = fract10;
        for (int i = 0; i < 5; i++) {
            value = value * target;
            sb.append(digits[(int)value]);
            value = value - (int)value;
        }
        return sb.toString();
    }

    private static void stage5() {
        try
        {
            Scanner sc = new Scanner(System.in);
            int sourceRadix = Integer.parseInt(sc.nextLine().trim());
            String number = sc.nextLine().trim();
            int targetRadix = Integer.parseInt(sc.nextLine().trim());
            if (sourceRadix < 1 || sourceRadix > 36 || targetRadix < 1 || targetRadix > 36) {
                System.out.println("Error! error - radix has to belong to [1,36]");
                return;
            }
            int value;
            if (targetRadix == sourceRadix) {
                System.out.println(number);
                return;
            }
            switch (sourceRadix) {
                case 1:
                    int count = number.length();
                    System.out.println(Integer.toString(count, targetRadix));
                    break;
                default:
                    String[] parts = number.split("\\.");
                    int int10 = Integer.parseInt(parts[0], sourceRadix);
                    double double10 = parts.length == 1 ? 0 : decimalStringToX(parts[1], sourceRadix);
                    if (targetRadix == 1)
                        System.out.println("1".repeat(int10));
                    else {
                        String intNewBase = Integer.toString(int10, targetRadix);
                        if (double10 == 0) {
                            System.out.println(intNewBase);
                        } else {
                            String doubleNewBase = convertFractionalToX(double10, targetRadix);
                            System.out.println(String.format("%s.%s", intNewBase, doubleNewBase));
                        }
                    }
            }
        } catch (NumberFormatException e) {
            System.out.println("error!");
        } catch (NoSuchElementException e) {
            System.out.println("error!");
        }
    }

    public static void main(String[] args) {
        stage5();
    }
}

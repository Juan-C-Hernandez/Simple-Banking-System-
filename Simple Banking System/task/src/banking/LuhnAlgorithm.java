package banking;

import java.util.Scanner;

public class LuhnAlgorithm {
    private static int luhnSum(String num) {
        int sum = 0;
        int digit;
        for(int i = 0; i < num.length(); i++) {
            digit = num.charAt(i) - '0';
            if (i % 2 == 0) {
                digit *= 2;
                digit = digit > 9 ? digit - 9 : digit;
            }
            sum += digit;
        }

        return sum;
    }

    public static boolean checkLuhnNumber(String cardNumber) {
        return luhnSum(cardNumber) % 10 == 0;
    }

    public static int luhnCheckSumDigit(String cardNumber) {
        int digit = 10 - (luhnSum(cardNumber) % 10);
        return digit == 10 ? 0 : digit;
    }

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        String cn = s.nextLine();
        int cs = luhnCheckSumDigit(cn);
        System.out.println(cs);
        System.out.println(cn + " " + cs);
        System.out.println(checkLuhnNumber(cn + "" + cs));
    }
}

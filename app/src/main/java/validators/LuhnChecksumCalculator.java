package validators;

import java.util.ArrayList;
import java.util.List;

public class LuhnChecksumCalculator {

    public static int calculate(long payload) {
        List<Integer> digitsFromRight = digitsReversed(payload);

        int multiplier = 2;
        int sumDigits = 0;

        for (int digit : digitsFromRight) {
            int multipliedValue = digit * multiplier;
            sumDigits += digitsSummed(multipliedValue);
            multiplier = multiplier == 2 ? 1 : 2;
        }

        return (10 - (sumDigits % 10)) % 10;
    }

    private static int digitsSummed(int number) {
        return digitsReversed(number).stream().mapToInt(Integer::intValue).sum();
    }

    private static List<Integer> digitsReversed(long number) {
        List<Integer> digits = new ArrayList<>();
        while (number > 0) {
            digits.add((int) (number % 10));
            number = number / 10;
        }
        return digits;
    }
}

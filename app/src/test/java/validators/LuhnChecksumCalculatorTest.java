package validators;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LuhnChecksumCalculatorTest {

    @Test
    void shouldCalculateExampleOneCorrectly() {
        // Example from https://en.wikipedia.org/wiki/Luhn_algorithm
        int checkDigit = LuhnChecksumCalculator.calculate(7992739871L);
        assertEquals(3, checkDigit);
    }

    @Test
    void shouldCalculateExampleTwoCorrectly() {
        // Example from https://sv.wikipedia.org/wiki/Personnummer_i_Sverige
        int checkDigit = LuhnChecksumCalculator.calculate(811218987L);
        assertEquals(6, checkDigit);
    }
}

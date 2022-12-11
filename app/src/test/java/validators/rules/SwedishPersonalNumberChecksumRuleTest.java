package validators.rules;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static validators.StringValidator.checksum;
import static validators.StringValidator.given;

class SwedishPersonalNumberChecksumRuleTest {

    @Test
    void shouldFailWithIncorrectCheckDigit() {
        List<String> messages = given("19701127-4192").validate(
                checksum().onFail("should have a correct checksum")
        );

        assertEquals(1, messages.size());
        assertEquals("should have a correct checksum", messages.get(0));
    }

    @Test
    void shouldPassWithCorrectCheckDigit() {
        List<String> messages = given("19701127-4193").validate(
                checksum().onFail("should have a correct checksum")
        );

        assertEquals(0, messages.size());
    }

    @Test
    void shouldPassWithoutCentury() {
        List<String> messages = given("701127-4193").validate(
                checksum().onFail("should have a correct checksum")
        );

        assertEquals(0, messages.size());
    }

    @Test
    void shouldPassWithPlus() {
        List<String> messages = given("201127+4194").validate(
                checksum().onFail("should have a correct checksum")
        );

        assertEquals(0, messages.size());
    }
}
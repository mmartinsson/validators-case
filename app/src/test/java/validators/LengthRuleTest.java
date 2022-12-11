package validators;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static validators.StringValidator.given;
import static validators.StringValidator.length;

class LengthRuleTest {

    @Test
    void shouldFailMinLength() {
        List<String> messages = given("ab").validate(
                length().min(3).onFail("should be at least 3 characters long")
        );

        assertEquals(1, messages.size());
        assertEquals("should be at least 3 characters long", messages.get(0));
    }

    @Test
    void shouldPassMinLength() {
        List<String> messages = given("abc").validate(
                length().min(3).onFail("should be at least 3 characters long")
        );

        assertEquals(0, messages.size());
    }

    @Test
    void shouldFailMaxLength() {
        List<String> messages = given("abcd").validate(
                length().max(3).onFail("should be at most 3 characters long")
        );

        assertEquals(1, messages.size());
        assertEquals("should be at most 3 characters long", messages.get(0));
    }

    @Test
    void shouldPassMaxLength() {
        List<String> messages = given("abc").validate(
                length().max(3).onFail("should be at most 3 characters long")
        );

        assertEquals(0, messages.size());
    }

    @Test
    void shouldPassMinMaxLength() {
        List<String> messages = given("abcd").validate(
                length().min(3).max(6).onFail("should be at most 3 characters long")
        );

        assertEquals(0, messages.size());
    }
}
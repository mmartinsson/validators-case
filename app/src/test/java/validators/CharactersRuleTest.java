package validators;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static validators.StringValidator.chars;
import static validators.StringValidator.given;

class CharactersRuleTest {

    @Test
    void shouldFailChars() {
        List<String> messages = given("abcd").validate(
                chars().accept("abc").onFail("should only contain a, b and c")
        );

        assertEquals(1, messages.size());
        assertEquals("should only contain a, b and c", messages.get(0));
    }

    @Test
    void shouldPassChars() {
        List<String> messages = given("abc").validate(
                chars().accept("abc").onFail("should only contain a, b and c")
        );

        assertEquals(0, messages.size());
    }

    @Test
    void shouldFailCount() {
        List<String> messages = given("abcd").validate(
                chars().count("[abc]", 4).onFail("should contain 4 of a, b and c")
        );

        assertEquals(1, messages.size());
        assertEquals("should contain 4 of a, b and c", messages.get(0));
    }

    @Test
    void shouldPassCount() {
        List<String> messages = given("abcc").validate(
                chars().count("[abc]", 4).onFail("should contain 4 of a, b and c")
        );

        assertEquals(0, messages.size());
    }
}
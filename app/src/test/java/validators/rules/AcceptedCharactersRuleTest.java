package validators.rules;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static validators.StringValidator.acceptedChars;
import static validators.StringValidator.given;

class AcceptedCharactersRuleTest {

    @Test
    void shouldFailChars() {
        List<String> messages = given("abcd").validate(
            acceptedChars().accept("abc").onFail("should only contain a, b and c")
        );

        assertEquals(1, messages.size());
        assertEquals("should only contain a, b and c", messages.get(0));
    }

    @Test
    void shouldPassChars() {
        List<String> messages = given("abc").validate(
            acceptedChars().accept("abc").onFail("should only contain a, b and c")
        );

        assertEquals(0, messages.size());
    }
}
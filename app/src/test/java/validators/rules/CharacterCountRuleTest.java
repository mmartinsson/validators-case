package validators.rules;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static validators.StringValidator.characterCount;
import static validators.StringValidator.given;

class CharacterCountRuleTest {

    @Test
    void shouldFail() {
        List<String> messages = given("abcd").validate(
            characterCount().includeChars("[abc]").acceptedCounts(4).onFail("should contain 4 of a, b and c")
        );

        assertEquals(1, messages.size());
        assertEquals("should contain 4 of a, b and c", messages.get(0));
    }

    @Test
    void shouldPass() {
        List<String> messages = given("abcc").validate(
            characterCount().includeChars("[abc]").acceptedCounts(4).onFail("should contain 4 of a, b and c")
        );

        assertEquals(0, messages.size());
    }
}
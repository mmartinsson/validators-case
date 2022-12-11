package validators;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static validators.StringValidator.exists;
import static validators.StringValidator.given;
import static validators.StringValidator.length;
import static validators.StringValidator.chars;
import static validators.StringValidator.checksum;

public class StringValidatorTest {

    @Test
    void shouldPassSwedishPersonalNumber() {
        List<String> messages = given("19701127+4193").validate(
                exists().onFail("should be a non-blank string"),
                length().max(13).onFailBreakWith("should be at most 13 characters long"),
                length().min(10).onFail("should be at least 10 characters long"),
                chars().accept("[0-9-+]*").onFail("should only contain, digits, - and +"),
                chars().count("[0-9]", 10, 12).onFail("should contain 10 or 12 digits"),
                checksum().onFail("should have a correct checksum")
        );

        assertEquals(0, messages.size());
    }

    @Test
    void shouldFailWithMultipleMessages() {
        List<String> messages = given("*").validate(
                exists().onFail("should be a non-blank string"),
                length().max(13).onFailBreakWith("should be at most 13 characters long"),
                length().min(10).onFail("should be at least 10 characters long"),
                chars().accept("[0-9-+]*").onFail("should only contain digits, - and +"),
                chars().count("[0-9]", 10, 12).onFail("should contain 10 or 12 digits"),
                checksum().onFail("should have a correct checksum")
        );

        assertEquals(4, messages.size());
        assertTrue(messages.contains("should be at least 10 characters long"));
        assertTrue(messages.contains("should only contain digits, - and +"));
        assertTrue(messages.contains("should contain 10 or 12 digits"));
        assertTrue(messages.contains("should be formatted correctly"));
    }

    @Test
    void shouldBreakOnFail() {
        List<String> messages = given("********************************").validate(
                exists().onFail("should be a non-blank string"),
                length().max(13).onFailBreakWith("should be at most 13 characters long"),
                length().min(10).onFail("should be at least 10 characters long"),
                chars().accept("[0-9-+]*").onFail("should only contain, digits, - and +"),
                chars().count("[0-9]", 10, 12).onFail("should contain 10 or 12 digits"),
                checksum().onFail("should have a correct checksum")
        );

        assertEquals(1, messages.size());
        assertTrue(messages.contains("should be at most 13 characters long"));
    }

    @Nested
    class PersonalName {

        @Test
        void shouldFailWhenNull() {
            List<String> messages = given(null).validate(
                    exists().onFailBreakWith("should be a non-blank string"),
                    chars().accept("[0-9-+]*").onFail("should only contain swedish alphabetical characters")
            );

            assertEquals(1, messages.size());
            assertTrue(messages.contains("should be a non-blank string"));
        }

        @Test
        void shouldFailWhenBlank() {
            List<String> messages = given("").validate(
                    exists().onFailBreakWith("should be a non-blank string"),
                    chars().accept("[0-9-+]*").onFail("should only contain swedish alphabetical characters")
            );

            assertEquals(1, messages.size());
            assertTrue(messages.contains("should be a non-blank string"));
        }

        @Test
        void shouldFailWhenNotSwedishAlphabeticalCharacters() {
            List<String> messages = given("ê").validate(
                    exists().onFailBreakWith("should be a non-blank string"),
                    chars().accept("[a-zA-ZåäöÅÄÖ]*").onFail("should only contain swedish alphabetical characters")
            );

            assertEquals(1, messages.size());
            assertTrue(messages.contains("should only contain swedish alphabetical characters"));
        }

        @Test
        void shouldPass() {
            List<String> messages = given("Åke Ärling Österlind").validate(
                    exists().onFailBreakWith("should be a non-blank string"),
                    chars().accept("[a-zA-ZåäöÅÄÖ ]*").onFail("should only contain swedish alphabetical characters")
            );

            assertEquals(0, messages.size());
        }
    }
}

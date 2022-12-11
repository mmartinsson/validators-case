package validators;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import validators.rules.ValidationRule;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static validators.StringValidator.characterCount;
import static validators.StringValidator.exists;
import static validators.StringValidator.given;
import static validators.StringValidator.length;
import static validators.StringValidator.acceptedChars;
import static validators.StringValidator.checksum;

public class StringValidatorTest {

    @Nested
    class SwedishPersonalNumber {

        private final List<ValidationRule> swedishPersonalNumberRules = List.of(
            exists().onFailBreakWith("should be a non-blank string"),
            length().max(13).onFailBreakWith("should be at most 13 characters long"),
            length().min(10).onFail("should be at least 10 characters long"),
            acceptedChars().accept("[0-9-+]*").onFail("should only contain, digits, - and +"),
            characterCount().includeChars("[0-9]").acceptedCounts(10, 12).onFail("should contain 10 or 12 digits"),
            checksum().onFail("should have a correct checksum")
        );

        @Test
        void shouldFailWhenNull() {
            List<String> messages = given(null).validate(swedishPersonalNumberRules);

            assertEquals(1, messages.size());
            assertTrue(messages.contains("should be a non-blank string"));
        }

        @Test
        void shouldFailWhenBlank() {
            List<String> messages = given("").validate(swedishPersonalNumberRules);
            assertTrue(messages.contains("should be a non-blank string"));
        }

        @Test
        void shouldPassSwedishPersonalNumber() {
            List<String> messages = given("19701127+4193").validate(swedishPersonalNumberRules);
            assertEquals(0, messages.size());
        }
    }

    @Nested
    class PersonalName {

        private final List<ValidationRule> swedishPersonalName = List.of(
                exists().onFailBreakWith("should be a non-blank string"),
                acceptedChars().accept("[a-zA-ZåäöÅÄÖ ]*").onFail("should only contain swedish alphabetical characters")
        );

        @Test
        void shouldFailWhenNull() {
            List<String> messages = given(null).validate(swedishPersonalName);

            assertEquals(1, messages.size());
            assertTrue(messages.contains("should be a non-blank string"));
        }

        @Test
        void shouldFailWhenBlank() {
            List<String> messages = given("").validate(swedishPersonalName);

            assertEquals(1, messages.size());
            assertTrue(messages.contains("should be a non-blank string"));
        }

        @Test
        void shouldFailWhenNotSwedishAlphabeticalCharacters() {
            List<String> messages = given("ê").validate(swedishPersonalName);

            assertEquals(1, messages.size());
            assertTrue(messages.contains("should only contain swedish alphabetical characters"));
        }

        @Test
        void shouldPass() {
            List<String> messages = given("Åke Ärling Österlind").validate(swedishPersonalName);
            assertEquals(0, messages.size());
        }
    }

    @Nested
    class Failure {

        @Test
        void shouldFailWithMultipleMessages() {
            List<String> messages = given("*").validate(
                    length().min(10).onFail("should be at least 10 characters long"),
                    acceptedChars().accept("[0-9]*").onFail("should only contain digits")
            );

            assertEquals(2, messages.size());
            assertTrue(messages.contains("should be at least 10 characters long"));
            assertTrue(messages.contains("should only contain digits"));
        }

        @Test
        void shouldBreakOnFail() {
            List<String> messages = given("*").validate(
                    length().min(10).onFailBreakWith("should be at least 10 characters long"),
                    acceptedChars().accept("[0-9]*").onFail("should only contain digits")
            );

            assertEquals(1, messages.size());
            assertTrue(messages.contains("should be at least 10 characters long"));
        }
    }
}

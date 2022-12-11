package validators;

import java.util.ArrayList;
import java.util.List;

class StringValidator {

    private static String value;

    static StringValidator given(String value) {
        StringValidator.value = value;
        return new StringValidator();
    }

    static ExistsRule exists() {
        return new ExistsRule();
    }

    static LengthRule length() {
        return new LengthRule();
    }

    static CharactersRule chars() {
        return new CharactersRule();
    }

    static SwedishPersonalNumberChecksumRule checksum() {
        return new SwedishPersonalNumberChecksumRule();
    }

    List<String> validate(ValidationRule... rules) {
        List<String> allMessages = new ArrayList<>();
        for (ValidationRule rule : rules) {
            List<String> ruleMessages = rule.validate(value);
            allMessages.addAll(ruleMessages);
            if (!ruleMessages.isEmpty() && rule.isBreakOnFail()) break;
        }
        return allMessages;
    }
}


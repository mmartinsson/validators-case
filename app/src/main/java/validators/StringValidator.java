package validators;

import validators.checksum.LuhnChecksumCalculator;
import validators.rules.CharactersRule;
import validators.rules.ExistsRule;
import validators.rules.LengthRule;
import validators.rules.SwedishPersonalNumberChecksumRule;
import validators.rules.ValidationRule;

import java.util.ArrayList;
import java.util.List;

public class StringValidator {

    private static String value;

    public static StringValidator given(String value) {
        StringValidator.value = value;
        return new StringValidator();
    }

    public static ExistsRule exists() {
        return new ExistsRule();
    }

    public static LengthRule length() {
        return new LengthRule();
    }

    public static CharactersRule chars() {
        return new CharactersRule();
    }

    public static SwedishPersonalNumberChecksumRule checksum() {
        return new SwedishPersonalNumberChecksumRule(new LuhnChecksumCalculator());
    }

    public List<String> validate(ValidationRule... rules) {
        List<String> allMessages = new ArrayList<>();
        for (ValidationRule rule : rules) {
            List<String> ruleMessages = rule.validate(value);
            allMessages.addAll(ruleMessages);
            if (!ruleMessages.isEmpty() && rule.isBreakOnFail()) break;
        }
        return allMessages;
    }
}


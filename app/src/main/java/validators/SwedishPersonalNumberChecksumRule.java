package validators;

import java.util.Collections;
import java.util.List;

public class SwedishPersonalNumberChecksumRule extends ValidationRule {

    @Override
    public List<String> validate(String swedishPersonalNumber) {
        // TODO This feels like a separate validation but this validator is dependent on it. Extract it or not?
        if (!swedishPersonalNumber.matches("^(\\d{6}|\\d{8})[-+]\\d{4}?"))
            return List.of("should be formatted correctly");

        int expectedCheckDigit = LuhnChecksumCalculator.calculate(payload(swedishPersonalNumber));
        if (expectedCheckDigit != checkDigit(swedishPersonalNumber)) return List.of(message);

        return Collections.emptyList();
    }

    private long payload(String swedishPersonalNumber) {
        String value = swedishPersonalNumber.replace("+", "").replace("-", "");
        if (value.length() == 12) value = value.substring(2);
        return Long.parseLong(value.substring(0, value.length() - 1));
    }

    private long checkDigit(String swedishPersonalNumber) {
        return Long.parseLong(swedishPersonalNumber.substring(swedishPersonalNumber.length() - 1));
    }
}

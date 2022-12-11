package validators.rules;

import validators.checksum.ChecksumCalculator;

import java.util.Collections;
import java.util.List;

public class SwedishPersonalNumberChecksumRule extends ValidationRule {

    private final ChecksumCalculator checksumCalculator;

    public SwedishPersonalNumberChecksumRule(ChecksumCalculator checksumCalculator) {
        this.checksumCalculator = checksumCalculator;
    }

    @Override
    public List<String> validate(String swedishPersonalNumber) {
        int expectedCheckDigit = checksumCalculator.calculate(payload(swedishPersonalNumber));
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

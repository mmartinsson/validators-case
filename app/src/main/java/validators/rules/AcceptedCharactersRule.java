package validators.rules;

import java.util.Collections;
import java.util.List;

public class AcceptedCharactersRule extends ValidationRule {

    private String acceptedRegex = ".*";

    public AcceptedCharactersRule accept(String regex) {
        this.acceptedRegex = regex;
        return this;
    }

    @Override
    public List<String> validate(String value) {
        if (!value.matches(acceptedRegex)) return List.of(message);
        return Collections.emptyList();
    }
}

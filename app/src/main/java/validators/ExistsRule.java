package validators;

import java.util.Collections;
import java.util.List;

public class ExistsRule extends ValidationRule {

    public List<String> validate(String value) {
        if (value == null) return List.of(message);
        if (value.isBlank()) return List.of(message);
        return Collections.emptyList();
    }
}

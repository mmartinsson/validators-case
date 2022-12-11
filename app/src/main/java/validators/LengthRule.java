package validators;

import java.util.Collections;
import java.util.List;

public class LengthRule extends ValidationRule {

    private int min = 0;
    private int max = Integer.MAX_VALUE;

    public LengthRule min(int min) {
        this.min = min;
        return this;
    }

    public LengthRule max(int max) {
        this.max = max;
        return this;
    }

    public List<String> validate(String value) {
        if (value.length() < min) return List.of(message);
        if (value.length() > max) return List.of(message);
        return Collections.emptyList();
    }
}

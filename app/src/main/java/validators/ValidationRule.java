package validators;

import java.util.List;

public abstract class ValidationRule {
    protected String message;
    private boolean breakOnFail = false;

    public ValidationRule onFail(String message) {
        this.message = message;
        return this;
    }

    public ValidationRule onFailBreakWith(String message) {
        this.message = message;
        this.breakOnFail = true;
        return this;
    }

    public boolean isBreakOnFail() {
        return breakOnFail;
    }

    public abstract List<String> validate(String value);
}

package validators.rules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CharacterCountRule extends ValidationRule {

    private Pattern includePattern = Pattern.compile(".*");
    private final List<Integer> acceptedCounts = new ArrayList<>();

    public CharacterCountRule includeChars(String includeRegex) {
        this.includePattern = Pattern.compile(includeRegex);
        return this;
    }

    public CharacterCountRule acceptedCounts(Integer... acceptedCounts) {
        this.acceptedCounts.addAll(Arrays.asList(acceptedCounts));
        return this;
    }

    @Override
    public List<String> validate(String value) {
        if (acceptedCounts.isEmpty()) return Collections.emptyList();

        Matcher matcher = includePattern.matcher(value);
        int count = (int) matcher.results().count();
        if (!acceptedCounts.contains(count)) return List.of(message);

        return Collections.emptyList();
    }
}

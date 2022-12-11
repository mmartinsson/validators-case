package validators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CharactersRule extends ValidationRule {

    private String acceptedRegex = ".*";
    private String countRegex = ".*";
    private final List<Integer> acceptedCounts = new ArrayList<>();

    public CharactersRule accept(String regex) {
        this.acceptedRegex = regex;
        return this;
    }

    public CharactersRule count(String countRegex, Integer... acceptedCounts) {
        this.countRegex = countRegex;
        this.acceptedCounts.addAll(Arrays.asList(acceptedCounts));
        return this;
    }

    @Override
    public List<String> validate(String value) {
        if (!value.matches(acceptedRegex)) return List.of(message);

        // TODO Move this to a separate count rule?
        if (countRegex != null && !acceptedCounts.isEmpty()) {
            Pattern pattern = Pattern.compile(countRegex);
            Matcher matcher = pattern.matcher(value);
            int count = (int) matcher.results().count();
            if (!acceptedCounts.contains(count)) {
                return List.of(message);
            }
        }

        return Collections.emptyList();
    }
}

import java.util.*;

public class AutocompleteSystem {
    private final Map<String, Integer> frequencies = new HashMap<>();

    public void recordSearch(String query) {
        frequencies.put(query, frequencies.getOrDefault(query, 0) + 1);
    }

    public List<String> getSuggestions(String prefix) {
        List<String> matches = new ArrayList<>();
        for (String key : frequencies.keySet()) {
            if (key.startsWith(prefix)) matches.add(key);
        }
        matches.sort((a, b) -> frequencies.get(b) - frequencies.get(a));
        return matches;
    }

    public static void main(String[] args) {
        AutocompleteSystem ac = new AutocompleteSystem();
        ac.recordSearch("java tutorial");
        ac.recordSearch("java tutorial");
        ac.recordSearch("javascript");
        ac.recordSearch("java download");

        System.out.println("Suggestions for 'jav': " + ac.getSuggestions("jav"));
    }
}
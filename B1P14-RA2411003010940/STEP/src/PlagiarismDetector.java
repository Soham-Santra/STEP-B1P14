import java.util.*;

public class PlagiarismDetector {
    // n-gram -> Set of Document Names
    private final Map<String, Set<String>> database = new HashMap<>();

    public void indexDocument(String name, String text) {
        for (String gram : getNGrams(text, 5)) {
            database.computeIfAbsent(gram, k -> new HashSet<>()).add(name);
        }
    }

    public void analyze(String name, String text) {
        List<String> grams = getNGrams(text, 5);
        Map<String, Integer> matchCounts = new HashMap<>();

        for (String gram : grams) {
            if (database.containsKey(gram)) {
                for (String otherDoc : database.get(gram)) {
                    if (!otherDoc.equals(name)) {
                        matchCounts.put(otherDoc, matchCounts.getOrDefault(otherDoc, 0) + 1);
                    }
                }
            }
        }

        matchCounts.forEach((doc, count) -> {
            double similarity = (count * 100.0) / grams.size();
            System.out.printf("Match with %s: %.2f%% similarity\n", doc, similarity);
        });
    }

    private List<String> getNGrams(String text, int n) {
        String[] words = text.toLowerCase().split("\\W+");
        List<String> grams = new ArrayList<>();
        for (int i = 0; i <= words.length - n; i++) {
            grams.add(String.join(" ", Arrays.copyOfRange(words, i, i + n)));
        }
        return grams;
    }

    public static void main(String[] args) {
        PlagiarismDetector detector = new PlagiarismDetector();
        detector.indexDocument("Original.txt", "The quick brown fox jumps over the lazy dog");
        detector.analyze("Student.txt", "A quick brown fox jumps over that lazy dog");
    }
}
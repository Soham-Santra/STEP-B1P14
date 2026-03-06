import java.util.*;

public class AnalyticsDashboard {
    private final Map<String, Integer> views = new HashMap<>();
    private final Map<String, Set<String>> uniqueUsers = new HashMap<>();
    private final Map<String, Integer> sources = new HashMap<>();

    public void processEvent(String url, String userId, String source) {
        views.put(url, views.getOrDefault(url, 0) + 1);
        uniqueUsers.computeIfAbsent(url, k -> new HashSet<>()).add(userId);
        sources.put(source, sources.getOrDefault(source, 0) + 1);
    }

    public void showDashboard() {
        System.out.println("--- TOP PAGES ---");
        views.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(5)
                .forEach(e -> System.out.println(e.getKey() + ": " + e.getValue() + " views (" + uniqueUsers.get(e.getKey()).size() + " unique)"));
    }

    public static void main(String[] args) {
        AnalyticsDashboard dashboard = new AnalyticsDashboard();
        dashboard.processEvent("/home", "user1", "google");
        dashboard.processEvent("/news", "user2", "facebook");
        dashboard.processEvent("/home", "user1", "google"); // Same user
        dashboard.processEvent("/home", "user3", "direct");
        dashboard.showDashboard();
    }
}
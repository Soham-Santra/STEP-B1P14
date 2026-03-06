import java.util.*;

public class MultiLevelCache {
    // L1: RAM (Limited to 2 items for testing LRU)
    private final LinkedHashMap<String, String> l1 = new LinkedHashMap<>(2, 0.75f, true) {
        protected boolean removeEldestEntry(Map.Entry eldest) { return size() > 2; }
    };

    // L2: SSD (Larger)
    private final Map<String, String> l2 = new HashMap<>();

    public String get(String key) {
        if (l1.containsKey(key)) {
            System.out.println(key + ": L1 HIT");
            return l1.get(key);
        }
        if (l2.containsKey(key)) {
            System.out.println(key + ": L2 HIT (Promoting to L1)");
            String val = l2.get(key);
            l1.put(key, val);
            return val;
        }
        System.out.println(key + ": CACHE MISS (Fetching from DB)");
        String val = "DataFor_" + key;
        l2.put(key, val);
        return val;
    }

    public static void main(String[] args) {
        MultiLevelCache cache = new MultiLevelCache();
        cache.get("Vid1"); // DB
        cache.get("Vid2"); // DB
        cache.get("Vid1"); // L1 Hit
        cache.get("Vid3"); // DB (Vid2 evicted from L1 to make room)
        cache.get("Vid2"); // L2 Hit (Promoted back to L1)
    }
}
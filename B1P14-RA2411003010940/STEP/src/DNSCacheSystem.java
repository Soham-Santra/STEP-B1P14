import java.util.*;
import java.util.concurrent.*;

public class DNSCacheSystem {
    static class DNSEntry {
        String ip;
        long expiryTime;

        DNSEntry(String ip, int ttlSeconds) {
            this.ip = ip;
            this.expiryTime = System.currentTimeMillis() + (ttlSeconds * 1000);
        }
    }

    private final Map<String, DNSEntry> cache = new ConcurrentHashMap<>();
    private int hits = 0, misses = 0;

    public String resolve(String domain) {
        DNSEntry entry = cache.get(domain);
        if (entry != null && System.currentTimeMillis() < entry.expiryTime) {
            hits++;
            System.out.println("Cache HIT: " + domain + " -> " + entry.ip);
            return entry.ip;
        }

        misses++;
        System.out.print("Cache MISS/EXPIRED. Querying upstream... ");
        String ip = "172.217." + new Random().nextInt(255) + ".1"; // Simulated upstream
        cache.put(domain, new DNSEntry(ip, 5)); // 5 second TTL for testing
        System.out.println("Resolved: " + ip);
        return ip;
    }

    public void printStats() {
        double rate = (hits + misses == 0) ? 0 : (hits * 100.0 / (hits + misses));
        System.out.printf("Stats: Hit Rate: %.1f%%\n", rate);
    }

    public static void main(String[] args) throws InterruptedException {
        DNSCacheSystem dns = new DNSCacheSystem();
        dns.resolve("google.com");
        dns.resolve("google.com"); // Hit
        dns.printStats();

        System.out.println("Waiting for TTL to expire...");
        Thread.sleep(6000);
        dns.resolve("google.com"); // Expired/Miss
    }
}
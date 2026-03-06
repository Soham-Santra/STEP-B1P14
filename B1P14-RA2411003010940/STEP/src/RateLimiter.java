import java.util.*;

public class RateLimiter {
    static class TokenBucket {
        int tokens = 5; // Small limit for testing
        long lastRefill = System.currentTimeMillis();
    }

    private final Map<String, TokenBucket> clients = new HashMap<>();

    public boolean isAllowed(String clientId) {
        TokenBucket bucket = clients.computeIfAbsent(clientId, k -> new TokenBucket());
        refill(bucket);

        if (bucket.tokens > 0) {
            bucket.tokens--;
            return true;
        }
        return false;
    }

    private void refill(TokenBucket bucket) {
        long now = System.currentTimeMillis();
        // Add 1 token every 2 seconds
        int newTokens = (int) ((now - bucket.lastRefill) / 2000);
        if (newTokens > 0) {
            bucket.tokens = Math.min(5, bucket.tokens + newTokens);
            bucket.lastRefill = now;
        }
    }

    public static void main(String[] args) {
        RateLimiter limiter = new RateLimiter();
        for (int i = 0; i < 7; i++) {
            System.out.println("Request " + (i+1) + ": " + (limiter.isAllowed("userA") ? "Allowed" : "Denied"));
        }
    }
}
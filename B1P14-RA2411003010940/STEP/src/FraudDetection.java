import java.util.*;

public class FraudDetection {
    static class Transaction {
        int id, amount;
        Transaction(int id, int amount) { this.id = id; this.amount = amount; }
    }

    public static void findPairs(List<Transaction> txns, int target) {
        Map<Integer, Transaction> seen = new HashMap<>();
        for (Transaction t : txns) {
            int complement = target - t.amount;
            if (seen.containsKey(complement)) {
                System.out.println("Suspicious Pair: ID " + seen.get(complement).id + " & ID " + t.id);
            }
            seen.put(t.amount, t);
        }
    }

    public static void main(String[] args) {
        List<Transaction> list = Arrays.asList(
                new Transaction(1, 500), new Transaction(2, 300), new Transaction(3, 200)
        );
        findPairs(list, 500); // Should find 300 + 200
    }
}
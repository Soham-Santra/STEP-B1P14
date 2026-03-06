import java.util.*;

// ============================================================
// STRATEGY PATTERN (UC 12)
// ============================================================
interface PalindromeStrategy {
    boolean check(String input);
}

class StackStrategy implements PalindromeStrategy {
    @Override
    public boolean check(String input) {
        java.util.Stack<Character> stack = new java.util.Stack<>();
        for (char c : input.toCharArray()) stack.push(c);
        for (char c : input.toCharArray()) {
            if (c != stack.pop()) return false;
        }
        return true;
    }
}

// ============================================================
// MAIN APP CLASS
// ============================================================
public class PalindromeCheckerApp {

    public static void main(String[] args) {
        displayWelcomeMessage(); // UC 1
        String target = "madam";

        // Call standard Use Cases
        checkByReversal(target);      // UC 3
        checkByTwoPointer(target);    // UC 4
        checkByStack(target);         // UC 5
        checkByStackAndQueue(target); // UC 6
        checkByDeque(target);         // UC 7
        checkByLinkedList(target);    // UC 8

        System.out.println("\n--- UC 9: Recursive Result ---");
        System.out.println("Is Palindrome? : " + checkRecursive(target, 0, target.length() - 1));

        System.out.println("\n--- UC 10: Normalized Result ---");
        checkNormalizedPalindrome("A man a plan a canal Panama");

        System.out.println("\n--- UC 11: OO Service Result ---");
        PalindromeService service = new PalindromeService();
        System.out.println("Is Palindrome? : " + service.checkPalindrome("racecar"));

        // --- UC 12 & 13: Strategy & Performance ---
        System.out.println("\n--- UC 13: Performance Comparison Result ---");
        PalindromeStrategy strategy = new StackStrategy(); // UC 12

        long startTime = System.nanoTime(); // UC 13
        boolean isPal = strategy.check("level");
        long endTime = System.nanoTime();   // UC 13

        System.out.println("Input : level");
        System.out.println("Is Palindrome? : " + isPal);
        System.out.println("Execution Time : " + (endTime - startTime) + " ns"); // UC 13
    }

    public static void displayWelcomeMessage() {
        System.out.println("========================================");
        System.out.println("   WELCOME TO PALINDROME CHECKER    ");
        System.out.println("========================================");
    }

    public static void checkByReversal(String original) {
        String reverse = new StringBuilder(original).reverse().toString();
        System.out.println("UC 3 (Reversal): " + original.equals(reverse));
    }

    public static void checkByTwoPointer(String original) {
        int left = 0, right = original.length() - 1;
        boolean isPal = true;
        while (left < right) {
            if (original.charAt(left++) != original.charAt(right--)) {
                isPal = false; break;
            }
        }
        System.out.println("UC 4 (Two-Pointer): " + isPal);
    }

    public static void checkByStack(String original) {
        Stack<Character> stack = new Stack<>();
        for (char c : original.toCharArray()) stack.push(c);
        String rev = "";
        while (!stack.isEmpty()) rev += stack.pop();
        System.out.println("UC 5 (Stack): " + original.equals(rev));
    }

    public static void checkByStackAndQueue(String original) {
        Stack<Character> stack = new Stack<>();
        Queue<Character> queue = new LinkedList<>();
        for (char c : original.toCharArray()) { stack.push(c); queue.add(c); }
        boolean match = true;
        while (!stack.isEmpty()) {
            if (!stack.pop().equals(queue.remove())) { match = false; break; }
        }
        System.out.println("UC 6 (Stack vs Queue): " + match);
    }

    public static void checkByDeque(String original) {
        Deque<Character> deque = new LinkedList<>();
        for (char c : original.toCharArray()) deque.addLast(c);
        boolean isPal = true;
        while (deque.size() > 1) {
            if (!deque.removeFirst().equals(deque.removeLast())) { isPal = false; break; }
        }
        System.out.println("UC 7 (Deque): " + isPal);
    }

    public static void checkByLinkedList(String original) {
        LinkedList<Character> list = new LinkedList<>();
        for (char c : original.toCharArray()) list.add(c);
        boolean isPal = true;
        while (list.size() > 1) {
            if (!list.removeFirst().equals(list.removeLast())) { isPal = false; break; }
        }
        System.out.println("UC 8 (Linked List): " + isPal);
    }

    private static boolean checkRecursive(String s, int start, int end) { // UC 9
        if (start >= end) return true;
        if (s.charAt(start) != s.charAt(end)) return false;
        return checkRecursive(s, start + 1, end - 1);
    }

    public static void checkNormalizedPalindrome(String input) { // UC 10
        String normalized = input.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
        boolean isPal = true;
        for (int i = 0; i < normalized.length() / 2; i++) {
            if (normalized.charAt(i) != normalized.charAt(normalized.length() - 1 - i)) {
                isPal = false; break;
            }
        }
        System.out.println("Input: " + input + " | Is Palindrome: " + isPal);
    }
}

class PalindromeService { // UC 11
    public boolean checkPalindrome(String input) {
        int start = 0, end = input.length() - 1;
        while (start < end) {
            if (input.charAt(start++) != input.charAt(end--)) return false;
        }
        return true;
    }
}
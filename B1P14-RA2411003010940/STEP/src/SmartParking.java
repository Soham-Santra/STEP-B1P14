public class SmartParking {
    private final String[] spots = new String[10]; // Small lot for testing

    public int park(String license) {
        int start = Math.abs(license.hashCode()) % spots.length;
        int current = start;

        do {
            if (spots[current] == null) {
                spots[current] = license;
                return current;
            }
            current = (current + 1) % spots.length; // Linear probing
            System.out.println("Collision at " + (current-1 == -1 ? 9 : current-1) + ", trying " + current);
        } while (current != start);

        return -1; // Lot full
    }

    public static void main(String[] args) {
        SmartParking lot = new SmartParking();
        System.out.println("Parked at: " + lot.park("CAR-1"));
        System.out.println("Parked at: " + lot.park("CAR-2"));
    }
}
import java.util.HashMap;
import java.util.Map;

/**
 * Simple Cache System.
 *
 * Demonstrates:
 * 1. Cache operations using HashMap
 * 2. Object references
 * 3. Garbage Collection (GC) eligibility
 *
 * Important:
 * Removing an object from the cache does not necessarily make it
 * eligible for GC. The object becomes eligible only when there are
 * no reachable references pointing to it.
 */
class Cache {

    // Key -> identifies the cached object
    // Value -> actual object stored in the cache
    private final Map<String, Object> cache = new HashMap<>();

    /**
     * Adds an object to the cache.
     * If the key already exists, its value is replaced.
     */
    public void put(String key, Object value) {
        cache.put(key, value);
    }

    /**
     * Retrieves an object from the cache.
     * Returns null if the key does not exist.
     */
    public Object get(String key) {
        return cache.get(key);
    }

    /**
     * Removes an object from the cache.
     *
     * Note:
     * This removes only the cache's reference. The object may still
     * be referenced elsewhere and therefore may not be eligible for GC.
     */
    public void remove(String key) {
        cache.remove(key);
    }

    /**
     * Removes all objects from the cache.
     *
     * Objects may still be reachable through references outside the cache.
     */
    public void clear() {
        cache.clear();
    }

    /**
     * Returns the number of entries currently in the cache.
     */
    public int size() {
        return cache.size();
    }
}

/**
 * Main class for demonstrating the Cache System
 * and Garbage Collection eligibility.
 */
public class CacheSystem {

    public static void main(String[] args) {

        Cache cache = new Cache();

        /*
         * Objects are created in Heap memory.
         *
         * user1 and user2 are local reference variables.
         */
        Object user1 = new String("User-1");
        Object user2 = new String("User-2");

        /*
         * Store objects in the cache.
         *
         * User-1 is now reachable through:
         *
         * user1 -----------------> User-1 object
         *                              ^
         *                              |
         * cache["user1"] --------------+
         */
        cache.put("user1", user1);
        cache.put("user2", user2);

        System.out.println("User 1: " + cache.get("user1"));
        System.out.println("User 2: " + cache.get("user2"));

        /*
         * Remove User-1 from the cache.
         *
         * The cache no longer references User-1, but user1 still
         * references it.
         *
         * Therefore User-1 is NOT eligible for GC yet.
         */
        cache.remove("user1");

        System.out.println("\nAfter removing user1 from cache:");
        System.out.println("Cache size: " + cache.size());

        /*
         * Remove the remaining reference to User-1.
         *
         * Now no reachable reference points to the User-1 object.
         * Therefore it becomes eligible for Garbage Collection.
         *
         * Eligible for GC does NOT mean immediately deleted.
         * The JVM decides when to perform GC.
         */
        user1 = null;

        System.out.println("\nUser-1 reference removed.");
        System.out.println("User-1 is now eligible for GC.");

        /*
         * Clear the cache.
         *
         * This removes the cache's reference to User-2.
         * However, user2 still references User-2.
         */
        cache.clear();

        System.out.println("\nCache cleared.");
        System.out.println("Cache size: " + cache.size());

        /*
         * Remove the final reference to User-2.
         *
         * User-2 now has no reachable references and becomes
         * eligible for Garbage Collection.
         */
        user2 = null;

        System.out.println("User-2 reference removed.");
        System.out.println("User-2 is now eligible for GC.");

        /*
         * System.gc() is only a request to the JVM.
         * It does NOT guarantee that GC will actually run.
         *
         * It is also NOT required to make an object eligible for GC.
         */
        System.gc();
    }
}
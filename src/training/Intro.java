package training;

/**
 * Former animated "wandering arrow" hint for language selection.
 * Disabled: the language flags are now always visible in the sidebar,
 * so no animated pointer is needed. Kept as a no-op for API compatibility.
 */
public class Intro {

    public Intro() {
        // intentionally empty – no animated arrow
    }

    public static void cancel() {
        // no-op
    }
}

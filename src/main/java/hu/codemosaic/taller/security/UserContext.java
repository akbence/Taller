package hu.codemosaic.taller.security;

public class UserContext {
    private static final ThreadLocal<String> loggedInUser = new ThreadLocal<>();

    public static void setUsername(String username) {
        loggedInUser.set(username);
    }

    public static String getUsername() {
        return loggedInUser.get();
    }

    public static void clear() {
        loggedInUser.remove();
    }
}

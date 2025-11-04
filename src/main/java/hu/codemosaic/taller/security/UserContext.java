package hu.codemosaic.taller.security;

import lombok.Setter;

import java.util.UUID;

@Setter
public class UserContext {
    private static final ThreadLocal<String> userName = new ThreadLocal<>();
    private static final ThreadLocal<UUID> userId = new ThreadLocal<>();

    public UserContext(String name, String id) {
        userName.set(name);
        userId.set(UUID.fromString(id));
    }

    public static void clear() {
        userName.remove();
        userId.remove();
    }

    public static String getUsername() {
        return userName.get();
    }

    public static UUID getUserId() {
        return userId.get();
    }

    public static void setUserId(UUID id){
        userId.set(id);
    }

    public static void setUsername(String username) {
        userName.set(username);
    }
}

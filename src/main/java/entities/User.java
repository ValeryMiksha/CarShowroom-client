package entities;

public class User {

    private int id;
    private String login;
    private String password;
    private String email;
    private boolean isAdmin;
    private boolean isLocked;

    private int wishListId;

    public void setId(int id) {
        this.id = id;
    }

    public int getWishListId() {
        return wishListId;
    }

    public void setWishListId(int wishListId) {
        this.wishListId = wishListId;
    }

    private static String currentUserLogin;
    private static int currentUserId;

    public static int getCurrentUserId() {
        return currentUserId;
    }

    public static void setCurrentUserId(int currentUserId) {
        User.currentUserId = currentUserId;
    }

    public static String getCurrentUserLogin() {
        return currentUserLogin;
    }

    public static void setCurrentUserLogin(String currentUserLogin) {
        User.currentUserLogin = currentUserLogin;
    }

    public User() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public int getId() {
        return id;
    }
}

package entities;

public class MessageToAdmin {

    private int id;

    private String userLogin;
    private String content;

    public MessageToAdmin() {
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

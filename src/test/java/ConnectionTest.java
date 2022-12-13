import commands.Commands;
import connection.Connection;
import entities.User;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class ConnectionTest {

    @Test
    public void sendObjectThroughSocket() throws IOException {
        Connection.makeConnection();
        User user=new User();
        user.setLogin("123");
        user.setEmail("123");
        user.setPassword("pass");
        user.setLocked(true);
        user.setAdmin(false);
        Connection.writeObject(user, Commands.GetUserInfoId);
    }
}

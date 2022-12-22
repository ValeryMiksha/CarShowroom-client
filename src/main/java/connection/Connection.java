package connection;

import commands.Commands;
import entities.Car;
import entities.User;
import org.json.JSONObject;

import java.io.*;
import java.net.Socket;

public class Connection {
    private static Socket socket;

    private static PrintWriter writer;
    private static BufferedReader reader;

    private static DataOutputStream dos;
    private static DataInputStream dis;

    private final static int PORT = 1024;
    private final static String HOST = "localhost";

    public static void makeConnection() throws IOException {
        socket = new Socket(HOST, PORT);
        dos = new DataOutputStream(socket.getOutputStream());
        writer = new PrintWriter(dos);
        dis=new DataInputStream(socket.getInputStream());
        reader=new BufferedReader(new InputStreamReader(dis));
    }

    public static void writeObject(User user, Commands commands) {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("login", user.getLogin());
        jsonObject.put("password", user.getPassword());
        jsonObject.put("email", user.getEmail());
        jsonObject.put("isAdmin", user.isAdmin());
        jsonObject.put("isLocked", user.isLocked());
        jsonObject.put("command", commands.ordinal());

        writer.println(jsonObject);
        writer.flush(); // в поток
    }
    public static void writeObject(Car car, Commands commands) {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("vendor", car.getVendor());
        jsonObject.put("model", car.getModel());
        jsonObject.put("yearOfIssue", car.getYearOfIssue());
        jsonObject.put("engineVolume", car.getEngineVolume());
        jsonObject.put("price", car.getPrice());
        jsonObject.put("transmission",car.getTransmission());
        jsonObject.put("command",commands.ordinal());

        writer.println(jsonObject);
        writer.flush();
    }
    public static void writeCarWithId(Car car, Commands commands) {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", car.getId());
        jsonObject.put("vendor", car.getVendor());
        jsonObject.put("model", car.getModel());
        jsonObject.put("yearOfIssue", car.getYearOfIssue());
        jsonObject.put("engineVolume", car.getEngineVolume());
        jsonObject.put("price", car.getPrice());
        jsonObject.put("transmission",car.getTransmission());
        jsonObject.put("command",commands.ordinal());

        writer.println(jsonObject);
        writer.flush();
    }


    public static String readObject()
    {
        String str = null;
        try {
            while (reader.ready())
                str = reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return str;
    }

}

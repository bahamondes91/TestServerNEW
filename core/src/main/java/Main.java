import Se.Iths.alexis.User;
import com.google.gson.Gson;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static List<String> billBoard = new ArrayList<>();

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newCachedThreadPool();

        try (ServerSocket socket = new ServerSocket(5050)) {

            System.out.println(Thread.currentThread().getName());
            while (true) {
                Socket client = socket.accept();
                executorService.submit(() -> handleConnection(client));


            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void handleConnection(Socket client) {
        try {

            var inputFromClient = new BufferedReader(new InputStreamReader((client.getInputStream())));
            var url = readRequest(inputFromClient);

            var outputToClient = client.getOutputStream();

            if (url.equals("/cat.png")) {
                sendCatResponse(outputToClient);
            } else if (url.equals("/dog.jpg")) {
                sendDogResponse(outputToClient);
            } else
                sendJsonResponse(outputToClient);

            inputFromClient.close();
            outputToClient.close();
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendDogResponse(OutputStream outputToClient) throws IOException {
        String header = "";
        byte[] data = new byte[0];
        File file = new File("core/src/main/resources/dog.jpg");
        if (!(file.exists() && !file.isDirectory())) {
            header = "HTTP/1.1 404 Not Found\r\nContent-length: 0\r\n\r\n";
        } else {
            try (FileInputStream fileInputStream = new FileInputStream(file)) {
                data = new byte[(int) file.length()];
                fileInputStream.read(data);
                header = "HTTP/1.1 200 OK\r\nContent-Type: image/jpg\r\nContent-length: " + data.length + "\r\n\r\n";
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        outputToClient.write(header.getBytes());
        outputToClient.write(data);

        outputToClient.flush();

    }

    private static void sendCatResponse(OutputStream outputToClient) throws IOException {
        String header = "";
        byte[] data = new byte[0];
        File file = new File("core/src/main/resources/cat.png");
        if (!(file.exists() && !file.isDirectory())) {
            header = "HTTP/1.1 404 Not Found\r\nContent-length: 0\r\n\r\n";
        } else {
            try (FileInputStream fileInputStream = new FileInputStream(file)) {
                data = new byte[(int) file.length()];
                fileInputStream.read(data);
                header = "HTTP/1.1 200 OK\r\nContent-Type: image/png\r\nContent-length: " + data.length + "\r\n\r\n";
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        outputToClient.write(header.getBytes());
        outputToClient.write(data);

        outputToClient.flush();

    }

    private static void sendJsonResponse(OutputStream outputToClient) throws IOException {


        var users = List.of(new User("51", "Alexis", "Aravena"), new User("52", "Dora", "mcflora"));

        Gson gson = new Gson();

        String json = gson.toJson(users);
        System.out.println(json);

        byte[] data = json.getBytes(StandardCharsets.UTF_8);

        String header = "HTTP/1.1 200 OK\r\nContent-Type: application/json\r\nContent-length: " + data.length + "\r\n\r\n";
        outputToClient.write(header.getBytes());
        outputToClient.write(data);
        outputToClient.flush();
    }

    private static String readRequest(BufferedReader inputFromClient) throws IOException {

        var url = "";

        while (true) {

            var line = inputFromClient.readLine();
            if (line.startsWith("GET"))
                url = line.split(" ")[1];
            if (line == null || line.isEmpty()) {
                break;
            }

            System.out.println(line);
        }

        return url;
    }

    private static void databaseConnection() {
        try {
            Class.forName("jdbc:sqlserver://");
            Connection con = DriverManager.getConnection("jdbc:sqlserver://localhost;user=Java;password=alexis;database=everyloop");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from users");
            while (rs.next())
                System.out.println(rs.getString(1) + " " + rs.getString(2));
            con.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }
}

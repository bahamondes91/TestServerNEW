import com.google.gson.Gson;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static List<String> billBoard = new ArrayList<>();

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newCachedThreadPool();

        try (ServerSocket socket = new ServerSocket(5050)) {


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
            System.out.println(client.getInetAddress());
            System.out.println(Thread.currentThread().getName());

            var inputFromClient = new BufferedReader(new InputStreamReader((client.getInputStream())));
            readRequest(inputFromClient);

            var outputToClient = client.getOutputStream();
            sendResponse(outputToClient);

            inputFromClient.close();
            outputToClient.close();
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendResponse(OutputStream outputToClient) throws IOException {

        //  List<Person> persons = new ArrayList<>();
//          persons.add(  new Person("Martin", 43, true));
//           persons.add( new Person("Kalle", 23, false));
//           persons.add( new Person("Anna", 11, true));

        var persons = List.of(new Person("Martin", 43, true),
                new Person("Martin", 43, true),
                new Person("Martin", 43, true));


        Gson gson = new Gson();

        String json = gson.toJson(persons);
        System.out.println(json);

        byte[] data = json.getBytes(StandardCharsets.UTF_8);

        String header = "HTTP/1.1 200 OK\r\nContent-Type: application/json\r\nContent-length: " + data.length + "\r\n\r\n";
        outputToClient.write(header.getBytes());
        outputToClient.write(data);


        // outputToClient.print("HTTP/1.1 404 Not Found\r\nContent-length: 0\r\n\r\n");
//        synchronized (billBoard) {
//            for (String line : billBoard) {
//                outputToClient.print(line + "\r\n");
//            }
//        }
//        outputToClient.print("\r\n");
        outputToClient.flush();
    }

    private static void readRequest(BufferedReader inputFromClient) throws IOException {
        List<String> tempList = new ArrayList<>();
        while (true) {

            var line = inputFromClient.readLine();
            if (line == null || line.isEmpty()) {
                break;
            }
            tempList.add(line);
            System.out.println(line);

        }
        synchronized (billBoard) {
            billBoard.addAll(tempList);
        }
    }
}

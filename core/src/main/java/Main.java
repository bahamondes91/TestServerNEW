import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {

        //localhost 127.0.0.1

        try (ServerSocket socket = new ServerSocket(5050)) {
            while(true){
            Socket client = socket.accept();
            System.out.println(client.getInetAddress());

            var inputFromClient = new BufferedReader(new InputStreamReader((client.getInputStream())));

            inputFromClient.lines().forEach(System.out::println);
            inputFromClient.close();
        }
//            String input = "";
//            while (input != null) {
//                input = inputFromClient.readLine();
//                System.out.println(input);
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

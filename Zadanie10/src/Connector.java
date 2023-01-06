import java.io.*;
import java.net.Socket;
import java.nio.Buffer;

public class Connector implements NetConnection {
    private Socket socket = null;
    private BufferedReader br = null;
    private DataInputStream input = null;
    private DataOutputStream out = null;
    String info;
    private Character searchedInt;
    private int repeatNum;
    @Override
    public void connect(String host, int port) {
        try {
            socket = new Socket(host, port);
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line;
            br.readLine(); // pierwsza linia z powitaniem
            br.readLine(); // uwaga
            // info czy to program czy nie
            PrintWriter pr = new PrintWriter(socket.getOutputStream());
            pr.println("program");
            pr.flush();
            br.readLine();
            info = br.readLine();
            searchedInt = info.charAt(info.length()-1);
            System.out.println("Searched int: " + searchedInt);
            while((line = br.readLine())!=null){
                if(line.charAt(0) == searchedInt ){
                    repeatNum++;
                }
            }

            pr.println(repeatNum);
            System.out.println(repeatNum);
            pr.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

//    public static void main(String[] args) {
//        String host = "172.30.24.101";
//        final int port = 8080;
//        Connector connect = new Connector();
//        connect.connect(host, port);
//    }
}

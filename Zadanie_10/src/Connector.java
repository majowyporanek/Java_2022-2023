
import javax.naming.InsufficientResourcesException;
import javax.sound.midi.SysexMessage;
import java.io.*;
import java.net.Socket;
import java.nio.Buffer;

public class Connector implements NetConnection {
    private Socket socket = null;
    private BufferedReader br = null;
    private DataInputStream input = null;
    private DataOutputStream out = null;
    String info;
    private String searchedInt;
    private int repeatNum;
    @Override
    public void connect(String host, int port) {
        try {
            socket = new Socket(host, port);
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line;
            String first = br.readLine(); // pierwsza linia z powitaniem
//            System.out.println(first);
            String second = br.readLine(); // uwaga
//            System.out.println(second);
            // info czy to program czy nie
            PrintWriter pr = new PrintWriter(socket.getOutputStream(), true);
            pr.println("program");
            br.readLine();
            info = br.readLine();
//            System.out.println(info);
            int i = 0;
            Character toFind = ':';
            while(!(info.charAt(i)==toFind)){
                i++;
            }
            searchedInt = info.substring(i+1, info.length()).trim();
//            System.out.println("Serarched int: " + searchedInt);
            //System.out.println("Searched int: " + searchedInt);
            while((line = br.readLine())!=null){
//                System.out.println(line);
                if(line.equals(searchedInt)){
                    repeatNum++;
                }
                if(line.equals("Twoja odpowiedz ??????")){
                    pr.println(Integer.toString(repeatNum));
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

//    public static void main(String[] args) {
//        String host = "172.30.24.101";
//        final int port = 8080;
//        Connector connect = new Connector();
//        connect.connect(host, port);
//    }
}

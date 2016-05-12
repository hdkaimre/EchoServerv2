import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Client {

    public static void main(String[] args) throws Exception {
        try(Socket socket = new Socket(InetAddress.getLocalHost(), 1337);
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream())) {
            for (String element : args) {
                dos.writeUTF(element);
                String word = dis.readUTF();
                System.out.println(word);
            }
        }
    }
}
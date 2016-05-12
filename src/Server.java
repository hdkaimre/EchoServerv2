import jdk.management.resource.internal.inst.DatagramDispatcherRMHooks;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

/**
 * Created by HansDaniel on 7.03.2016.
 */
public class Server implements Runnable {
    Socket socket;

    public Server(Socket socket) {
        this.socket = socket;
    }

    public static void main(String[] args) throws IOException {
        int port = 1337;
        ServerSocket serverSocket = new ServerSocket(port);
        try {
            while (true) {
                Socket socket = serverSocket.accept();
                Thread server = new Thread(new Server(socket));
                server.start();
            }
        }
        finally {
            serverSocket.close();
        }

    }


    @Override
    public void run() {
        try {
            process();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void process() throws IOException {
        try (DataInputStream dis = new DataInputStream(socket.getInputStream());
             DataOutputStream dos = new DataOutputStream(socket.getOutputStream())) {
            while (true) {
                try {
                    String in = dis.readUTF();
                    dos.writeUTF(in);
                }
                catch (EOFException e){
                    break;
                }
            }
        } finally {
            socket.close();
        }
    }


}


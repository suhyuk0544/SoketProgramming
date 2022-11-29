import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerReceiver extends Thread {
    Socket socket;
    DataOutputStream dataOutputStream;
    DataInputStream dataInputStream;

    TcpIpServer tcpIpServer;

    ServerReceiver(Socket socket){
        this.socket = socket;
        try {
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        String name = "";
        try {
            name = dataInputStream.readUTF();

            tcpIpServer.SendAll(name + "이 들어옴");

            tcpIpServer.c.put(name, dataOutputStream);
            System.out.println("서버 접속자: "+ tcpIpServer.c.size());

            while (dataInputStream != null){
                tcpIpServer.SendAll(dataInputStream.readUTF());
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            tcpIpServer.SendAll(name+"이 나감");

            tcpIpServer.c.remove(name);
        }
    }
}

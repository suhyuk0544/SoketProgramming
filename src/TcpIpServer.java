import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.SortedMap;

public class TcpIpServer {

    HashMap c;

    public static void main(String[] args) {
        try {
            new TcpIpServer().TcpIpServerStart();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    TcpIpServer(){
        c = new HashMap();
        Collections.synchronizedMap(c);
    }

    public void TcpIpServerStart() throws Exception {
        ServerSocket serverSocket = new ServerSocket(8081);

        while (true) {
            Socket socket = serverSocket.accept();

            System.out.println("{"+socket.getInetAddress()+":"+socket.getPort()+"} 접속");

            ServerReceiver t = new ServerReceiver(socket);
            t.start();

        }

    }

    void SendAll(String msg){
        Iterator it = c.keySet().iterator();

        while (it.hasNext()){
            try {
                DataOutputStream dataOutputStream = (DataOutputStream) c.get(it.next());
                dataOutputStream.writeUTF(msg);
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }



}
